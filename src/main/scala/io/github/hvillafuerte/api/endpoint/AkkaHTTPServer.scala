package io.github.hvillafuerte.api.endpoint




import akka.actor.ActorSystem
import akka.http.scaladsl.Http

import akka.http.scaladsl.server.RouteConcatenation._
import akka.stream.ActorMaterializer


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}



object AkkaHTTPServer extends App {

  // A K K A
  private implicit val actorSystem: ActorSystem = ActorSystem("products-service-application")
  private implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

  val routes = UsersEndpoint.getUserById ~
    UsersEndpoint.getUsersByQuery ~
    SubjectsEndpoint.getSubjectById ~
    SubjectsEndpoint.getSubjectsByQuery ~
    UniversitiesEndpoint.getCollegeById ~
    UniversitiesEndpoint.getCollegeByQuery

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


