package io.github.hvillafuerte.application

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import io.github.hvillafuerte.application.UsersBusinessLogic.User

class UsersBusinessLogic {

  val henry = User(1,"Henry", "Sevilla", 18, true)
  val pepe = User(2,"Pepe", "Valencia", 23, false)
  val laura = User(3,"Laura", "Salamanca", 21, false)
  val francisco = User(4,"Francisco", "Santander", 22, true)
  val cristina = User(5,"Cristina", "Barcelona", 26, false)
  val paula = User(6,"Paula", "Galicia", 20, false)


  val users = List[User](
    henry,pepe,laura,francisco,cristina,paula
  )


  def getUserById(id:Int): Future[Right[Nothing, User]] =
  Future(Right(users.find(user => user.userId == id ).head))

  def getUsersByQuery(single: Option[Boolean]): Future[Right[Nothing, List[User]]] =
    Future(Right(
      single
        .map(s => users.filter(user => user.single == s))
        .getOrElse(users)
    ))

  def addUser(user: User): User =
    (user :: users).map(_ => user).head

}


object UsersBusinessLogic {

  case class User(userId: Int, name: String, city: String, age: Int, single: Boolean)

}
