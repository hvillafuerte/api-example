package io.github.hvillafuerte.application

import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import io.github.hvillafuerte.infrastructure.UniversitiesRepository
import scala.concurrent.Future

class UniversitiesBusinessLogic (repository1: UniversitiesRepository){


  def getUniversityById(id:Int): Future[Right[Nothing, University]] =
    Future(Right(repository1.findById(id)))

  def getUniversityByCity(city:String): Future[Right[Nothing, List[University]]] =
    Future(Right(repository1.findByCity(city)))

  def getUniversitiesByQuery(online: Option[Boolean]): Future[Right[Nothing, List[University]]] =
    Future(Right(
      online
        .map(s => repository1.findAllOnline(s))
        .getOrElse(repository1.findAll())
    ))

}

object UniversitiesBusinessLogic {

  case class University(id: Int, name: String, city: String, year: Int, online: Boolean)

}

