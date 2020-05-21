package io.github.hvillafuerte.application

import io.github.hvillafuerte.application.TeachersBusinessLogic.Teacher
import io.github.hvillafuerte.infrastructure.TeachersRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class TeachersBusinessLogic (repository3: TeachersRepository){

  def getTeacherById (idTeacher: Int): Future[Right[Nothing, Teacher]] =
    Future(Right(repository3.findById(idTeacher)))

  def createTeacher (teacher: Teacher): Future[Right[Nothing, Long]] =
    Future(Right(repository3.createTeacher(teacher)))

}

object TeachersBusinessLogic {

  case class Teacher(idTeacher: Int, name: String, subject: String,city: String)

}