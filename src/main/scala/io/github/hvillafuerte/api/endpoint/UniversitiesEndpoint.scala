package io.github.hvillafuerte.api.endpoint


import sttp.tapir._
import sttp.tapir.server.akkahttp._
import io.github.hvillafuerte.application.UniversitiesBusinessLogic
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University


class UniversitiesEndpoint(app: UniversitiesBusinessLogic) {

  val getUniversityById = endpoint
    .get
    .in("universities" / path[Int]("id"))
    .out(anyJsonBody[University])
    .toRoute( id => app.getUniversityById(id))

  val getUniversityByQuery = endpoint
    .get
    .in("universities")
    .in(query[Option[Boolean]]("online"))
    .out(anyJsonBody[List[University]])
    .toRoute ( online => app.getUniversitiesByQuery(online))

}
