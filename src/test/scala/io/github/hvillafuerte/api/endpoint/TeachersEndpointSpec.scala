package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.TeachersBusinessLogic
import io.github.hvillafuerte.infrastructure.TeachersRepository
import org.scalatest.flatspec.AnyFlatSpecLike

class TeachersEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest{

  val repository3: TeachersRepository = new TeachersRepository()

  val app = new TeachersBusinessLogic(repository3)
  val teachersEndpoint = new TeachersEndpoint(app)

  it should "get teacher by idTeacher" in {

    val idTeacher = 1

    Get (s"/teachers/$idTeacher") ~> teachersEndpoint.getTeacherByIdApi ~> check{

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"idTeacher":1,"name":"Juan Jose Murillo","subject":"Java Programming","city":"Madrid"}""")
    }
  }


}
