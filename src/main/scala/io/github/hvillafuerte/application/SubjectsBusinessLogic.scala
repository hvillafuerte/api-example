package io.github.hvillafuerte.application


import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject

import scala.concurrent.Future

class SubjectsBusinessLogic {

  val subject1 = Subject(1,2020,"SubjectName1", true)
  val subject2 = Subject(2,2020,"SubjectName2", false)
  val subject3 = Subject(3,2020,"SubjectName3", true)
  val subject4 = Subject(4,2020,"SubjectName4", false)
  val subject5 = Subject(5,2020,"SubjectName5", true)
  val subject6 = Subject(6,2020,"SubjectName6", false)


  val subjects = List[Subject](
    subject1,subject2,subject3,subject4,subject5,subject6
  )


  def getSubjectById(id:Int): Future[Right[Nothing, Subject]] =
    Future(Right(subjects.find(subject => subject.id == id ).head))

  def getSubjectsByQuery(approvedSubject: Option[Boolean]): Future[Right[Nothing, List[Subject]]] =
    Future(Right(
      approvedSubject
        .map(filterByApprovedSubject)
        .getOrElse(subjects)
    ))

  def filterByApprovedSubject(approvedSubject: Boolean) =
    subjects.filter(subject => subject.approvedSubject == approvedSubject)

  def addSubject(subject: Subject): Subject=
    (subject :: subjects).map(_ => subject).head
}

object SubjectsBusinessLogic {

  case class Subject(id: Int, year: Int, subject_name: String, approvedSubject: Boolean)

}
