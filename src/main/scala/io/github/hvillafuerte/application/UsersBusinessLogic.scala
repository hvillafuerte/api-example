package io.github.hvillafuerte.application

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.UsersBusinessLogic.User
import io.github.hvillafuerte.infrastructure.UsersRepository

class UsersBusinessLogic (repository: UsersRepository) {


  def getUserById(id:Int): Future[Right[Nothing, User]] =
    Future(Right(repository.findById(id)))

  def getUsersByQuery(single: Option[Boolean]): Future[Right[Nothing, List[User]]] =
    Future(Right(
      single
        .map(s => repository.findAllSingle(s))
        .getOrElse(repository.findAll())
    ))
  def createUser(user: User): Future[Right[Nothing, Long]] =
    Future(Right(repository.createUser(user)))
}


object UsersBusinessLogic {

  case class User(userId: Int, name: String, city: String, age: Int, single: Boolean)

}
