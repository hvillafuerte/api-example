package io.github.hvillafuerte.api.endpoint


import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}


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



  val getUserById = endpoint
    .get
    .in("users" / path[Int]("userId"))
    .out(anyJsonBody[User])
    .toRoute( id => Future(Right(users.find(user => user.userId == id ).head)))


  val getUsersByQuery =
    endpoint
      .get
      .in("users")
      .in(query[Option[Boolean]]("single"))
      .out(anyJsonBody[List[User]])
      .toRoute { single => Future(Right(
        single
          .map(s => users.filter(user => user.single == s))
          .getOrElse(users)
      ))}



}


object UsersEndpoint extends UsersEndpoint


