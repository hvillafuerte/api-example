package io.github.hvillafuerte.application


import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import io.github.hvillafuerte.infrastructure.SubjectsRepository

import scala.concurrent.Future

class SubjectsBusinessLogic (repository2: SubjectsRepository) {


  def getSubjectById(id:Int): Future[Right[Nothing, Subject]] =
    Future(Right(repository2.findById(id)))

  def getSubjectsByQuery(approvedSubject: Option[Boolean]): Future[Right[Nothing, List[Subject]]] =
    Future(Right(
      approvedSubject
        .map(s => repository2.findApprovedSubject(s) )
        .getOrElse(repository2.findAll())
    ))

  def createSubject(subject: Subject): Future[Right[Nothing, Long]]=
    Future(Right(repository2.createSubject(subject)))
}

object SubjectsBusinessLogic {

  case class Subject(id: Int, year: Int, subject_name: String, approvedSubject: Boolean)

}
