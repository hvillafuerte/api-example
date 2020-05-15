package io.github.hvillafuerte.application

import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import io.github.hvillafuerte.infrastructure.UniversitiesRepository
import scala.concurrent.Future

class UniversitiesBusinessLogic (repository: UniversitiesRepository){


  def getUniversityById(id:Int): Future[Right[Nothing, University]] =
    Future(Right(repository.findById(id)))

  def getUniversityByCity(city:String): Future[Right[Nothing, List[University]]] =
    Future(Right(repository.findByCity(city)))

  def getUniversitiesByQuery(online: Option[Boolean]): Future[Right[Nothing, List[University]]] =
    Future(Right(
      online
        .map(s => repository.findAllOnline(s))
        .getOrElse(repository.findAll())
    ))

}

object UniversitiesBusinessLogic {

  case class University(id: Int, name: String, city: String, year: Int, online: Boolean)

}

