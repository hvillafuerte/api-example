package io.github.hvillafuerte.api.endpoint

import sttp.tapir.json.circe._
import io.circe.generic.auto._
import io.github.hvillafuerte.application.TeachersBusinessLogic
import io.github.hvillafuerte.application.TeachersBusinessLogic.Teacher
import sttp.tapir._
import sttp.tapir.server.akkahttp._

class TeachersEndpoint (app: TeachersBusinessLogic){

  val getTeacherById: Endpoint[Int, Unit, Teacher, Nothing] = endpoint
      .get
      .in("teachers" /path[Int]("idTeacher"))
      .out(anyJsonBody[Teacher])

  val getTeacherByIdApi = getTeacherById
    .toRoute(idTeacher => app.getTeacherById(idTeacher))

}
