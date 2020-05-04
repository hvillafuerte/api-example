package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.SubjectsBusinessLogic
import org.scalatest.flatspec.AnyFlatSpecLike

class SubjectsEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest {

  private val subjectsBusinessLogic = new SubjectsBusinessLogic()
  private val subjectsEndpoint = new SubjectsEndpoint(subjectsBusinessLogic)


  it should ("get subject by Id") in {

    val SubjectId = 1

    Get(s"/subjects/${SubjectId}") ~> subjectsEndpoint.getSubjectById ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"id":1,"year":2020,"subject_name":"SubjectName1","approved_subject":true}""")

    }

  }
  it should ("get all subjects") in {

    Get(s"/subjects") ~> subjectsEndpoint.getSubjectsByQuery ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"year":2020,"subject_name":"SubjectName1","approved_subject":true},{"id":2,"year":2020,"subject_name":"SubjectName2","approved_subject":false},{"id":3,"year":2020,"subject_name":"SubjectName3","approved_subject":true},{"id":4,"year":2020,"subject_name":"SubjectName4","approved_subject":false},{"id":5,"year":2020,"subject_name":"SubjectName5","approved_subject":true},{"id":6,"year":2020,"subject_name":"SubjectName6","approved_subject":false}]""")

    }
  }

  it should ("get approved_subject = true") in {

    Get(s"/subjects?approved_subject=true") ~> subjectsEndpoint.getSubjectsByQuery ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":1,"year":2020,"subject_name":"SubjectName1","approved_subject":true},{"id":3,"year":2020,"subject_name":"SubjectName3","approved_subject":true},{"id":5,"year":2020,"subject_name":"SubjectName5","approved_subject":true}]""")

    }
  }

  it should ("get approved_subject = false") in {

    Get(s"/subjects?approved_subject=false") ~> subjectsEndpoint.getSubjectsByQuery ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"id":2,"year":2020,"subject_name":"SubjectName2","approved_subject":false},{"id":4,"year":2020,"subject_name":"SubjectName4","approved_subject":false},{"id":6,"year":2020,"subject_name":"SubjectName6","approved_subject":false}]""")

    }
  }

}