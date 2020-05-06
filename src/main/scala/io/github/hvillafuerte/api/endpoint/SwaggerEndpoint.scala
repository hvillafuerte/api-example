package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.github.hvillafuerte.application._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._

trait SwaggerEndpoint {

  private val usersApplication = new UsersBusinessLogic()
    val usersEndpoint = new UsersEndpoint(usersApplication)
  private val subjectsApplication = new SubjectsBusinessLogic()
   val subjectsEndpoint = new SubjectsEndpoint(subjectsApplication)
  private val universitiesApplication = new UniversitiesBusinessLogic()
   val universitiesEndpoint = new UniversitiesEndpoint (universitiesApplication)
  private val marksApplication = new MarksBusinessLogic()
  val marksEndpoint = new MarksEndpoint(marksApplication)
  private val booksApplication = new BooksBusinessLogic()
   val booksEndpoint = new BooksEndpoint(booksApplication)



  private lazy val openApi: String = Seq(
    usersEndpoint.getUserById,
    usersEndpoint.getUsersByQuery,
    subjectsEndpoint.getSubjectById,
    subjectsEndpoint.getSubjectsByQuery,
    universitiesEndpoint.getUniversityById,
    universitiesEndpoint.getUniversityByQuery,
    marksEndpoint.getMarkById,
    marksEndpoint.getMarks,
    booksEndpoint.getBookBySbn,
    booksEndpoint.getBooks
  ).toOpenAPI("api-example", "0.1.0").toYaml

  private lazy val contextPath = "docs"
  private lazy val yamlName    = "docs.yaml"

  val swagger = s"/${contextPath}"

  lazy val route: Route =
    pathPrefix(contextPath) {
      pathEndOrSingleSlash {
        redirect(s"$contextPath/index.html?url=/$contextPath/$yamlName", StatusCodes.PermanentRedirect)
      } ~ path(yamlName) {
        complete(openApi)
      } ~ getFromResourceDirectory("META-INF/resources/webjars/swagger-ui/3.25.0/")
    }


}

object SwaggerEndpoint extends SwaggerEndpoint