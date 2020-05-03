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
  val  UAM = University(1,"Universidad Autonoma de Madrid","Madrid",2019,true)
  val  UB = University(2,"Universidad de Burgos","Burgos",2018,false)
  val UAL = University(3,"Universidad de Almeria","Almeria",2020,true)
  val UGR = University(4,"Universidad de Granada","Granada",2020,true)
  val USAL = University(5,"Universidad de Salamanca","Salamanca",2019,false)
  val US = University(6,"Universidad de Sevilla","Sevilla",2018,true)

  val universities = List[University](
    UAM, UB,UAL,UGR,USAL,US
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