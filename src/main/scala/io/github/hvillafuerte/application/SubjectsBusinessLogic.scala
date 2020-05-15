package io.github.hvillafuerte.application


import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import io.github.hvillafuerte.infrastructure.SubjectsRepository

import scala.concurrent.Future

class SubjectsBusinessLogic (repository: SubjectsRepository) {


  def getSubjectById(id:Int): Future[Right[Nothing, Subject]] =
    Future(Right(repository.findById(id)))

  def getSubjectsByQuery(approvedSubject: Option[Boolean]): Future[Right[Nothing, List[Subject]]] =
    Future(Right(
      approvedSubject
        .map(s => repository.findApprovedSubject(s) )
        .getOrElse(repository.findAll())
    ))

  def createSubject(subject: Subject): Future[Right[Nothing, Long]]=
    Future(Right(repository.createSubject(subject)))
}

object SubjectsBusinessLogic {

  case class Subject(id: Int, year: Int, subject_name: String, approvedSubject: Boolean)

}
