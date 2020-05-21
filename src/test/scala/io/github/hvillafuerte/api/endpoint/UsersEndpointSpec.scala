package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.UsersBusinessLogic
import io.github.hvillafuerte.infrastructure.UsersRepository
import org.scalatest.flatspec.AnyFlatSpecLike


class UsersEndpointSpec  extends AnyFlatSpecLike with ScalatestRouteTest{

  val repository: UsersRepository = new UsersRepository()

  val app = new UsersBusinessLogic(repository)
  val usersEndpoint = new UsersEndpoint(app)

  it should("get user by Id") in {

    val userId = 1

    Get (s"/users/$userId") ~> usersEndpoint.getUserByIdApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"userId":1,"name":"Henry","city":"Madrid","age":30,"single":true}""")

    }

  }

  it should("get all users") in {

    Get (s"/users") ~> usersEndpoint.getUsersByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"userId":1,"name":"Henry","city":"Madrid","age":30,"single":true},{"userId":2,"name":"Pepe","city":"Valencia","age":33,"single":true},{"userId":3,"name":"Maria","city":"Salamanca","age":23,"single":true},{"userId":4,"name":"Francisco","city":"Burgos","age":22,"single":true},{"userId":5,"name":"Laura","city":"Malaga","age":28,"single":false},{"userId":6,"name":"Luis","city":"Granada","age":31,"single":false}]""")

    }

  }


  it should("get all users single = true") in {

    Get (s"/users?single=true") ~> usersEndpoint.getUsersByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"userId":1,"name":"Henry","city":"Madrid","age":30,"single":true},{"userId":2,"name":"Pepe","city":"Valencia","age":33,"single":true},{"userId":3,"name":"Maria","city":"Salamanca","age":23,"single":true},{"userId":4,"name":"Francisco","city":"Burgos","age":22,"single":true}]""")

    }

  }

  it should("get all users single = false") in {

    Get (s"/users?single=false") ~> usersEndpoint.getUsersByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"userId":5,"name":"Laura","city":"Malaga","age":28,"single":false},{"userId":6,"name":"Luis","city":"Granada","age":31,"single":false}]""")

    }

  }

  ignore should ("post a user") in {
    Post (s"/users") ~> usersEndpoint.getUsersByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """???""")

    }

  }

}
