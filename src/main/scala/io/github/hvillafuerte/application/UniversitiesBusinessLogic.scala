package io.github.hvillafuerte.application

import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University

import scala.concurrent.Future

class UniversitiesBusinessLogic {

  val  UAM = University(1,"Universidad Autonoma de Madrid","Madrid",2019,true)
  val  UB = University(2,"Universidad de Burgos","Burgos",2018,false)
  val UAL = University(3,"Universidad de Almeria","Almeria",2020,true)
  val UGR = University(4,"Universidad de Granada","Granada",2020,true)
  val USAL = University(5,"Universidad de Salamanca","Salamanca",2019,false)
  val US = University(6,"Universidad de Sevilla","Sevilla",2018,true)

  val universities = List[University](
    UAM, UB,UAL,UGR,USAL,US
  )

  def getUniversityById(id:Int): Future[Right[Nothing, University]] =
    Future(Right(universities.find(university => university.id == id ).head))

  def getUniversitiesByQuery(online: Option[Boolean]): Future[Right[Nothing, List[University]]] =
    Future(Right(
      online
        .map(s => universities.filter(university => university.online == s))
        .getOrElse(universities)
    ))



}

object UniversitiesBusinessLogic {

  case class University(id: Int, name: String, city: String, year: Int, online: Boolean)

}

