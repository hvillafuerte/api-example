package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.github.hvillafuerte.application._
import io.github.hvillafuerte.infrastructure.{SubjectsRepository, TeachersRepository, UniversitiesRepository, UsersRepository}
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._

trait SwaggerEndpoint {

  val repository: UsersRepository = new UsersRepository()
  val repository1: UniversitiesRepository = new UniversitiesRepository()
  val repository2: SubjectsRepository = new SubjectsRepository()
  val repository3: TeachersRepository = new TeachersRepository()

  private val usersApplication = new UsersBusinessLogic(repository)
    val usersEndpoint = new UsersEndpoint(usersApplication)
  private val subjectsApplication = new SubjectsBusinessLogic(repository2)
   val subjectsEndpoint = new SubjectsEndpoint(subjectsApplication)
  private val universitiesApplication = new UniversitiesBusinessLogic(repository1)
   val universitiesEndpoint = new UniversitiesEndpoint (universitiesApplication)
  private val marksApplication = new MarksBusinessLogic()
  val marksEndpoint = new MarksEndpoint(marksApplication)
  private val booksApplication = new BooksBusinessLogic()
   val booksEndpoint = new BooksEndpoint(booksApplication)
  private val teachersApplication = new TeachersBusinessLogic(repository3)
    val teachersEndpoint = new TeachersEndpoint(teachersApplication)



  private lazy val openApi: String = Seq(
    usersEndpoint.createUser,
    usersEndpoint.getUserById,
    usersEndpoint.getUsersByQuery,
    subjectsEndpoint.createSubject,
    subjectsEndpoint.getSubjectById,
    subjectsEndpoint.getSubjectsByQuery,
    universitiesEndpoint.getUniversityById,
    universitiesEndpoint.getUniversityByCity,
    universitiesEndpoint.getUniversityByQuery,
    marksEndpoint.getMarkById,
    marksEndpoint.getMarks,
    booksEndpoint.getBookBySbn,
    booksEndpoint.getBooks,
    teachersEndpoint.getTeacherById,
    teachersEndpoint.createTeacher
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