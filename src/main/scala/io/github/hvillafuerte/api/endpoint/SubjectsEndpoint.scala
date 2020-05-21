package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.server.Route
import sttp.tapir._
import sttp.tapir.server.akkahttp._
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.SubjectsBusinessLogic
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject


class SubjectsEndpoint(app: SubjectsBusinessLogic) {

  val getSubjectById: Endpoint[Int, Unit, Subject, Nothing] = endpoint
    .get
    .in("subjects" / path[Int]("id"))
    .out(anyJsonBody[Subject])

    val getSubjectByIdApi: Route = getSubjectById
    .toRoute( id => app.getSubjectById(id))


  val getSubjectsByQuery: Endpoint[Option[Boolean], Unit, List[Subject], Nothing] = endpoint
      .get
      .in("subjects")
      .in(query[Option[Boolean]]("approvedSubject"))
      .out(anyJsonBody[List[Subject]])

    val getSubjectsByQueryApi = getSubjectsByQuery
      .toRoute ( approved_subject => app.getSubjectsByQuery(approved_subject))


  val createSubject = endpoint
      .post
      .in("subjects")
      .in(anyJsonBody[Subject])
      .out(anyJsonBody[Long])

    val createSubjectApi = createSubject
      .toRoute(subject => app.createSubject(subject))
}


