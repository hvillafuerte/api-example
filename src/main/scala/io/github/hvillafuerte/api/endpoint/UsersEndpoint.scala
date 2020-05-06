package io.github.hvillafuerte.api.endpoint


import sttp.tapir._
import sttp.tapir.server.akkahttp._
import io.github.hvillafuerte.application.UsersBusinessLogic



import sttp.tapir.json.circe._
import io.circe.generic.auto._

import io.github.hvillafuerte.application.UsersBusinessLogic.User


 class UsersEndpoint(app: UsersBusinessLogic) {


  val getUserById = endpoint
    .get
    .in("users" / path[Int]("userId"))
    .out(anyJsonBody[User])

    val getUserByIdApi = getUserById
    .toRoute( id => app.getUserById(id))


  val getUsersByQuery = endpoint
      .get
      .in("users")
      .in(query[Option[Boolean]]("single"))
      .out(anyJsonBody[List[User]])


     val getUsersByQueryApi = getUsersByQuery
      .toRoute ( single => app.getUsersByQuery(single))



}





