package io.github.hvillafuerte.api.endpoint

import scala.concurrent.Future
import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}


import sttp.tapir.json.circe._
import io.circe.generic.auto._


trait UniversitiesEndpoint {

  case class University(id: Int, name: String, city: String, year: Int, online: Boolean)
  val college1 = University(1,"College1","Madrid",2019,true)
  val college2 = University(2,"College2","Burgos",2018,false)
  val college3 = University(3,"College3","Almeria",2020,true)
  val college4 = University(4,"College4","Granada",2020,true)
  val college5 = University(5,"College5","Salamanca",2019,false)
  val college6 = University(6,"College6","Sevilla",2018,true)

  val universities = List[University](
    college1, college2,college3,college4,college5,college6
  )

  val getUniversityById = endpoint
    .get
    .in("universities" / path[Int]("id"))
    .out(anyJsonBody[University])
    .toRoute( id => Future(Right(universities.find(university => university.id == id ).head)))

  val getUniversityByQuery = endpoint
    .get
    .in("universities")
    .in(query[Option[Boolean]]("online"))
    .out(anyJsonBody[List[University]])
    .toRoute{ online => Future(Right(online.map(s => universities.filter(university => university.online == s)).getOrElse(universities)))}
}

object UniversitiesEndpoint extends UniversitiesEndpoint