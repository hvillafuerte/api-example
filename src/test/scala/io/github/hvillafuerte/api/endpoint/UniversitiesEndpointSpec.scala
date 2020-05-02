package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.flatspec.AnyFlatSpecLike

class UniversitiesEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest{

  it should("get university by Id") in {

    val UniversityId = 1

    Get (s"/universities/${UniversityId}") ~> UniversitiesEndpoint.getUniversityById ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"id":1,"name":"College1","city":"Madrid","year":2019,"online":true}""")

    }

  }



}
