package io.github.hvillafuerte.application

import io.github.hvillafuerte.application.TeachersBusinessLogic.Teacher

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class TeachersBusinessLogic {

  val teacher1=Teacher(1,"Javier","Java")
  val teacher2=Teacher(2,"Jose","SQL")
  val teacher3=Teacher(3,"Melon","HTML")

  val teachers=List(teacher1, teacher2, teacher3)

  def getTeacherById (idTeacher: Int): Future[Right[Nothing, Teacher]] =
    Future(Right(teachers.find(teacher => teacher.idTeacher equals idTeacher).head))
}

object TeachersBusinessLogic {

  case class Teacher(idTeacher: Int, name: String, subject: String)

}