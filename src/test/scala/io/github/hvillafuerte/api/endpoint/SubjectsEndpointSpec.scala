package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.SubjectsBusinessLogic
import io.github.hvillafuerte.infrastructure.SubjectsRepository
import org.scalatest.flatspec.AnyFlatSpecLike

class SubjectsEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest {

  val repository: SubjectsRepository = new SubjectsRepository()
  val app = new SubjectsBusinessLogic(repository)
  val subjectsEndpoint = new SubjectsEndpoint(app)


  it should ("get subject by Id") in {

    val SubjectId = 1

    Get(s"/subjects/${SubjectId}") ~> subjectsEndpoint.getSubjectByIdApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"id":1,"year":2018,"subject_name":"Java Programming","approvedSubject":false}""")

    }

  }
  it should ("get all subjects") in {

    Get(s"/subjects") ~> subjectsEndpoint.getSubjectsByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"year":2018,"subject_name":"Java Programming","approvedSubject":false},{"id":2,"year":2017,"subject_name":"All about sql","approvedSubject":true},{"id":3,"year":2020,"subject_name":"http Server","approvedSubject":false},{"id":4,"year":2020,"subject_name":"Scala for Beginners","approvedSubject":true},{"id":5,"year":2019,"subject_name":"IntelliJ Idea","approvedSubject":true},{"id":6,"year":2019,"subject_name":"bbdd","approvedSubject":true}]""")

    }
  }

  it should ("get approvedSubject = true") in {

    Get(s"/subjects?approvedSubject=true") ~> subjectsEndpoint.getSubjectsByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":2,"year":2017,"subject_name":"All about sql","approvedSubject":true},{"id":4,"year":2020,"subject_name":"Scala for Beginners","approvedSubject":true},{"id":5,"year":2019,"subject_name":"IntelliJ Idea","approvedSubject":true},{"id":6,"year":2019,"subject_name":"bbdd","approvedSubject":true}]""")

    }
  }

  it should ("get approvedSubject = false") in {

    Get(s"/subjects?approvedSubject=false") ~> subjectsEndpoint.getSubjectsByQueryApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"year":2018,"subject_name":"Java Programming","approvedSubject":false},{"id":3,"year":2020,"subject_name":"http Server","approvedSubject":false}]""")

    }
  }

}