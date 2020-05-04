package io.github.hvillafuerte.application

import io.github.hvillafuerte.application.MarksBusinessLogic.Mark
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class MarksBusinessLogic {

  val mark1 = Mark(1,8,"1/5/2020")
  val mark2 = Mark(2,7,"30/4/2020")
  val mark3 = Mark(3,5,"25/4/2020")
  val mark4 = Mark(4,8,"2/5/2020")
  val mark5 = Mark(5,7,"26/4/2020")
  val mark6 = Mark(6,5,"25/4/2020")

  val marks = List (mark1,mark2,mark3,mark4,mark5,mark6)

  def getMarkById (id: Int): Future[Right[Nothing, Mark]] =
    Future(Right(marks.find(mark => mark.id == id).head))

  def getMarks : Future[Right[Nothing,List[Mark]]] =
    Future(Right(marks))

}

object MarksBusinessLogic {

  case class Mark(id: Int, mark: Int, date: String)

}
