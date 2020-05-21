package io.github.hvillafuerte.infrastructure


import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.UsersBusinessLogic.User
import scalikejdbc.{AutoSession, ConnectionPool, _}

class UsersRepository {


  private val config: Config = ConfigFactory.load().getConfig("infrastructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session = AutoSession

  def findById(id: Int): User =
    sql"SELECT * FROM USERS WHERE ID = $id;"
      .stripMargin
      .map(mapToUser)
      .list()
      .apply()
      .head

  def findAllSingle(single: Boolean): List[User] =
    sql"SELECT * FROM USERS WHERE SINGLE = $single;"
      .stripMargin
      .map(mapToUser)
      .list()
      .apply()

  def createUser(user: User): Long =
    sql"""
         |INSERT INTO USERS(ID, NAME, CITY, AGE, SINGLE)
         |VALUES (${user.userId}, ${user.name}, ${user.city}, ${user.age}, ${user.single})
      """
      .stripMargin
      .map(mapToUser)
      .updateAndReturnGeneratedKey()
      .apply()

  def findAll(): List[User] =
    sql"SELECT * FROM USERS;"
      .stripMargin
      .map(mapToUser)
      .list()
      .apply()

  def mapToUser(rs: WrappedResultSet): User =
    User(rs.int("ID"),
      rs.string("NAME"),
      rs.string("CITY"),
      rs.int("AGE"),
      rs.boolean("SINGLE"))

}
