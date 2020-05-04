package io.github.hvillafuerte.api.endpoint

import sttp.tapir._
import sttp.tapir.server.akkahttp._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.SubjectsBusinessLogic
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject


class SubjectsEndpoint(app: SubjectsBusinessLogic) {

  val getSubjectById = endpoint
    .get
    .in("subjects" / path[Int]("id"))
    .out(anyJsonBody[Subject])
    .toRoute( id => app.getSubjectById(id))


  val getSubjectsByQuery = endpoint
      .get
      .in("subjects")
      .in(query[Option[Boolean]]("approved_subject"))
      .out(anyJsonBody[List[Subject]])
      .toRoute ( approved_subject => app.getSubjectsByQuery(approved_subject))

}


