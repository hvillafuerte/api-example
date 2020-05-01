package io.github.hvillafuerte.api.endpoint

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._
import akka.stream.ActorMaterializer
import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

import sttp.tapir.json.circe._
import io.circe.generic.auto._

trait UsersEndpoint {

  case class User(userId: Int, name: String, city: String, age: Int, single: Boolean)
  val henry = User(1,"Henry", "Sevilla", 18, true)
  val pepe = User(2,"Pepe", "Valencia", 23, false)
  val laura = User(3,"Laura", "Salamanca", 21, false)
  val francisco = User(4,"Francisco", "Santander", 22, true)
  val cristina = User(5,"Cristina", "Barcelona", 26, false)
  val paula = User(6,"Paula", "Galicia", 20, false)


  val users = List[User](
    henry,pepe,laura,francisco,cristina,paula
  )

  val getUsers: Route = endpoint.get
    .in("users")
    .out(stringBody)
    .toRoute(_ => Future(Right("")))




  val getUserById = endpoint
    .get
    .in("users" / path[Int]("userId"))
    .out(anyJsonBody[User])
    .toRoute( id => Future(Right(users.find(user => user.userId == id ).head)))


  val getUsersByQuery =
    endpoint
      .get
      .in("users")
      .in(query[Boolean]("single"))
      .out(anyJsonBody[List[User]])
      .toRoute ( single => Future(Right(users.filter(user => user.single == single))))



}


object UsersEndpoint extends UsersEndpoint


object AkkaHTTPServer extends App {

  // A K K A
  private implicit val actorSystem: ActorSystem = ActorSystem("products-service-application")
  private implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

  val routes = UsersEndpoint.getUserById ~ UsersEndpoint.getUsersByQuery

  // R U N  A P P L I C A T I O N
  val serverBinding: Future[Http.ServerBinding] =
    Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) =>
      actorSystem.log.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")

    case Failure(e) =>
      actorSystem.log.error("Server error", e)
      actorSystem.terminate()
  }

  Await.result(actorSystem.whenTerminated, Duration.Inf)

}


