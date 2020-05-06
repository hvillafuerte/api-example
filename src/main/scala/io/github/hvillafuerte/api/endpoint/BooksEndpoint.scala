package io.github.hvillafuerte.api.endpoint

import akka.http.scaladsl.server.Route
import io.github.hvillafuerte.application.BooksBusinessLogic
import io.github.hvillafuerte.application.BooksBusinessLogic.Book
import sttp.tapir.json.circe._
import io.circe.generic.auto._
import sttp.tapir._
import sttp.tapir.server.akkahttp._

import scala.concurrent.Future

class BooksEndpoint (app: BooksBusinessLogic){

  val getBookBySbn = endpoint
    .get
    .in ("books" /path[Int]("sbn"))
    .out (anyJsonBody[Book])

    val getBookBySbnApi = getBookBySbn
    .toRoute(sbn => app.getBookBySbn(sbn))

  val getBooks: Endpoint[Unit, Unit, List[Book], Nothing] = endpoint
    .get
    .in("books")
    .out(anyJsonBody[List[Book]])

    val getBooksApi: Route = getBooks
    .toRoute(_ => app.getBooks)

}
