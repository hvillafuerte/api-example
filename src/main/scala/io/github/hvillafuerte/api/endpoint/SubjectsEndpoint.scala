package io.github.hvillafuerte.api.endpoint

import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}

import sttp.tapir.json.circe._
import io.circe.generic.auto._


trait SubjectsEndpoint {

  case class Subject(id: Int, year: Int, subject_name: String, approved_subject: Boolean)
  val subject1 = Subject(1,2020,"SubjectName1", true)
  val subject2 = Subject(2,2020,"SubjectName2", false)
  val subject3 = Subject(3,2020,"SubjectName3", true)
  val subject4 = Subject(4,2020,"SubjectName4", false)
  val subject5 = Subject(5,2020,"SubjectName5", true)
  val subject6 = Subject(6,2020,"SubjectName6", false)

  val subjects = List[Subject](
    subject1,subject2,subject3,subject4,subject5,subject6
  )

  val getSubjectById = endpoint
    .get
    .in("subjects" / path[Int]("id"))
    .out(anyJsonBody[Subject])
    .toRoute( id => Future(Right(subjects.find(Subject => Subject.id == id ).head)))


  val getSubjectsByQuery = endpoint
      .get
      .in("subjects")
      .in(query[Option[Boolean]]("approved_subject"))
      .out(anyJsonBody[List[Subject]])
      .toRoute { approved_subject => Future(Right(
        approved_subject
          .map(s => subjects.filter(Subject => Subject.approved_subject == s))
          .getOrElse(subjects)
      ))}


}


object SubjectsEndpoint extends SubjectsEndpoint

