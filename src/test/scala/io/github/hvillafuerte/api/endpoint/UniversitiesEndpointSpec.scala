package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.UniversitiesBusinessLogic
import io.github.hvillafuerte.infrastructure.UniversitiesRepository
import org.scalatest.flatspec.AnyFlatSpecLike

class UniversitiesEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest{

  private val app= new UniversitiesBusinessLogic(repository1)
  private val universitiesEndpoint = new UniversitiesEndpoint(app)

  val repository1: UniversitiesRepository = new UniversitiesRepository()


  it should("get university by Id") in {

    val UniversityId = 1

    Get (s"/universities/${UniversityId}") ~> universitiesEndpoint.getUniversityByIdApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"id":1,"name":"Universidad Autonoma de Madrid","city":"Madrid","year":2019,"online":true}""")

    }

  }


  it should ("get all universities") in {

    Get(s"/universities") ~> universitiesEndpoint.getUniversityByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"name":"Universidad Autonoma de Madrid","city":"Madrid","year":2019,"online":true},{"id":2,"name":"Universidad de Burgos","city":"Burgos","year":2018,"online":false},{"id":3,"name":"Universidad de Almeria","city":"Almeria","year":2020,"online":true},{"id":4,"name":"Universidad de Granada","city":"Granada","year":2020,"online":true},{"id":5,"name":"Universidad de Salamanca","city":"Salamanca","year":2019,"online":false},{"id":6,"name":"Universidad de Sevilla","city":"Sevilla","year":2018,"online":true}]""")

    }
  }

  it should ("get university online = true") in {

    Get(s"/universities?online=true") ~> universitiesEndpoint.getUniversityByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"name":"Universidad Autonoma de Madrid","city":"Madrid","year":2019,"online":true},{"id":3,"name":"Universidad de Almeria","city":"Almeria","year":2020,"online":true},{"id":4,"name":"Universidad de Granada","city":"Granada","year":2020,"online":true},{"id":6,"name":"Universidad de Sevilla","city":"Sevilla","year":2018,"online":true}]""")

    }

  }

  it should ("get university online = false") in {

    Get(s"/universities?online=false") ~> universitiesEndpoint.getUniversityByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":2,"name":"Universidad de Burgos","city":"Burgos","year":2018,"online":false},{"id":5,"name":"Universidad de Salamanca","city":"Salamanca","year":2019,"online":false}]""")

    }

  }

}
