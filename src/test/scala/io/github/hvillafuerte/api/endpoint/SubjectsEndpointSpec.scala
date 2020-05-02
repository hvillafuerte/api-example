package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.flatspec.AnyFlatSpecLike

class SubjectsEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest {

  it should ("get subject by Id") in {

    val SubjectId = 1

    Get(s"/subjects/${SubjectId}") ~> SubjectsEndpoint.getSubjectById ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"id":1,"year":2020,"subject_name":"SubjectName1","approved_subject":true}""")

    }

  }
}
