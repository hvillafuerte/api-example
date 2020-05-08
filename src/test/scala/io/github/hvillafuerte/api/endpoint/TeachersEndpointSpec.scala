package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.TeachersBusinessLogic
import org.scalatest.flatspec.AnyFlatSpecLike

class TeachersEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest{


  val app = new TeachersBusinessLogic
  val teachersEndpoint = new TeachersEndpoint(app)

  it should "get teacher by idTeacher" in {

    val idTeacher = 1

    Get (s"/teachers/${idTeacher}") ~> teachersEndpoint.getTeacherByIdApi ~> check{

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"idTeacher":1,"name":"Javier","subject":"Java"}""")
    }
  }


}
