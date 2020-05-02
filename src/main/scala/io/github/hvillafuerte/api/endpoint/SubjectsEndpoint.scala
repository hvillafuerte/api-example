package io.github.hvillafuerte.api.endpoint

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._
import akka.stream.ActorMaterializer
import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

import sttp.tapir.json.circe._
import io.circe.generic.auto._


trait SubjectsEndpoint {

  case class Subject(id: Int, agno: Int, subject_name: String, approved_subject: Boolean)
  val subject1 = Subject(1,2020,"SubjectName1", true)
  val subject2 = Subject(2,2020,"SubjectName2", false)
  val subject3 = Subject(3,2020,"SubjectName3", true)
  val subject4 = Subject(4,2020,"SubjectName4", false)
  val subject5 = Subject(5,2020,"SubjectName5", true)
  val subject6 = Subject(6,2020,"SubjectName6", false)

  val subjects = List[Subject](
    subject1,subject2,subject3,subject4,subject5,subject6
  )

  val getSubjectById = endpoint
    .get
    .in("subjects" / path[Int]("id"))
    .out(anyJsonBody[Subject])
    .toRoute( id => Future(Right(subjects.find(Subject => Subject.id == id ).head)))


  val getSubjectsByQuery = endpoint
      .get
      .in("subjects")
      .in(query[Option[Boolean]]("approved_subject"))
      .out(anyJsonBody[List[Subject]])
      .toRoute { approved_subject => Future(Right(
        approved_subject
          .map(s => subjects.filter(Subject => Subject.approved_subject == s))
          .getOrElse(subjects)
      ))}


  /*crear varias asignaturas

  visualizar todas, por agno,
  */
}


object SubjectsEndpoint extends SubjectsEndpoint

object AkkaHTTPServerSubjects extends App {

  // A K K A
  private implicit val actorSystem: ActorSystem = ActorSystem("products-service-application")
  private implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

  val routes = SubjectsEndpoint.getSubjectById ~ SubjectsEndpoint.getSubjectsByQuery

  // R U N  A P P L I C A T I O N
  val serverBinding: Future[Http.ServerBinding] =
    Http().bindAndHandle(routes, "localhost", 8080)

  serverBinding.onComplete {
    case Success(bound) =>
      actorSystem.log.info(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")

    case Failure(e) =>
      actorSystem.log.error("Server error", e)
      actorSystem.terminate()
  }

  Await.result(actorSystem.whenTerminated, Duration.Inf)

}