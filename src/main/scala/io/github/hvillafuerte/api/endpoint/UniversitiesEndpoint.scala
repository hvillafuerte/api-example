package io.github.hvillafuerte.api.endpoint


import akka.http.scaladsl.server.Route
import sttp.tapir._
import sttp.tapir.server.akkahttp._
import io.github.hvillafuerte.application.UniversitiesBusinessLogic
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University


class UniversitiesEndpoint(app: UniversitiesBusinessLogic) {

  val getUniversityById: Endpoint[Int, Unit, University, Nothing] = endpoint
    .get
    .in("universities" / path[Int]("id"))
    .out(anyJsonBody[University])

    val getUniversityByIdApi: Route = getUniversityById
    .toRoute( id => app.getUniversityById(id))

  val getUniversityByCity = endpoint
    .get
    .in("universities" / path[String]("city"))
    .out(anyJsonBody[List[University]])

    val getUniversityByCityApi: Route = getUniversityByCity
      .toRoute( city => app.getUniversityByCity(city))

  val getUniversityByQuery = endpoint
    .get
    .in("universities")
    .in(query[Option[Boolean]]("online"))
    .out(anyJsonBody[List[University]])

    val getUniversityByQueryApi = getUniversityByQuery
    .toRoute ( online => app.getUniversitiesByQuery(online))

}
