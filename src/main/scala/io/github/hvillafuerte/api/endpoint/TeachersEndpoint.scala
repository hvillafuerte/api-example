package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.server.Route
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

    val getTeacherByIdApi: Route = getTeacherById
      .toRoute(idTeacher => app.getTeacherById(idTeacher))

  val createTeacher: Endpoint[Teacher, Unit, Long, Nothing] = endpoint
    .post
    .in("teachers")
    .in(anyJsonBody[Teacher])
    .out(anyJsonBody[Long])

    val createTeacherApi: Route = createTeacher
      .toRoute( teacher => app.createTeacher(teacher))

}
