package io.github.hvillafuerte.api.endpoint


import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import io.github.hvillafuerte.application.BooksBusinessLogic
import org.scalatest.flatspec.AnyFlatSpecLike


class BooksEndpointSpec extends AnyFlatSpecLike with ScalatestRouteTest {

  val app = new BooksBusinessLogic()
  val booksEndpoint = new BooksEndpoint(app)

  it should "get all books" in {
    Get("/books") ~> booksEndpoint.getBooksApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """[{"sbn":1,"bookName":"History","date":"01/01/2020"},{"sbn":2,"bookName":"SpanishLanguage","date":"01/02/2020"},{"sbn":3,"bookName":"MySQL","date":"01/12/2019"}]""")
    }

  }

  it should "get bookBySbn" in {

    val sbn = 1

    Get(s"/books/${sbn}") ~> booksEndpoint.getBookBySbnApi ~> check {

      assert(status == StatusCodes.OK)
      assert(responseAs[String] == """{"sbn":1,"bookName":"History","date":"01/01/2020"}""")

    }
  }

}
