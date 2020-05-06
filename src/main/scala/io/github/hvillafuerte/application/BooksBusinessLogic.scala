package io.github.hvillafuerte.application


import io.github.hvillafuerte.application.BooksBusinessLogic.Book

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class BooksBusinessLogic {

  val book1 = Book(1,"History","01/01/2020")
  val book2 = Book(2,"SpanishLanguage","01/02/2020")
  val book3 = Book(3,"MySQL","01/12/2019")

  val books = List(book1,book2,book3)

  def getBookBySbn (sbn: Int): Future[Right[Nothing, Book]] =
    Future(Right(books.find(book => book.sbn == sbn).head))

  def getBooks : Future[Right[Nothing,List[Book]]] =
    Future(Right(books))

  def addBook (book: Book): Book =
    (book :: books).map(_ => book).head

}

object BooksBusinessLogic{

  case class Book(sbn: Int, bookName: String, date: String)
}