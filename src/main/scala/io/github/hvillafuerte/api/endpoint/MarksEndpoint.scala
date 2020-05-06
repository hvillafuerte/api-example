package io.github.hvillafuerte.api.endpoint

import scala.concurrent.Future
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.MarksBusinessLogic
import io.github.hvillafuerte.application.MarksBusinessLogic.Mark
import sttp.tapir._
import sttp.tapir.server.akkahttp._

class MarksEndpoint (app: MarksBusinessLogic) {

  val getMarkById = endpoint
    .get
    .in("marks" /path[Int]("id"))
    .out(anyJsonBody[Mark])

    val getMarkByIdApi = getMarkById
    .toRoute (id => app.getMarkById(id))

  val getMarks = endpoint
    .get
    .in("marks")
    .out(anyJsonBody[List[Mark]])

    val getMarksApi = getMarks
    .toRoute(_ => app.getMarks)
}
