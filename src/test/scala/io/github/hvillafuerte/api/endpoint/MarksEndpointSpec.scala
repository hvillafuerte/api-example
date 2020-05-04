package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.MarksBusinessLogic
import org.scalatest.flatspec.AnyFlatSpecLike

class MarksEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest {

  val app = new MarksBusinessLogic()
  val marksEndpoint = new MarksEndpoint(app)

  it should "get all marks" in {
    Get("/marks") ~> marksEndpoint.getMarks ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"mark":8,"date":"1/5/2020"},{"id":2,"mark":7,"date":"30/4/2020"},{"id":3,"mark":5,"date":"25/4/2020"},{"id":4,"mark":8,"date":"2/5/2020"},{"id":5,"mark":7,"date":"26/4/2020"},{"id":6,"mark":5,"date":"25/4/2020"}]""")
    }

  }
}