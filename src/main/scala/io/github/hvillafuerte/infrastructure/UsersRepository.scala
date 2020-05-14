package io.github.hvillafuerte.infrastructure


import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import io.github.hvillafuerte.application.UsersBusinessLogic.User
import scalikejdbc.{AutoSession, ConnectionPool}
import scalikejdbc._

class UsersRepository {


  private val config: Config = ConfigFactory.load().getConfig("infrastructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session = AutoSession

  def findById(id: Int):User =
    sql"""
      |SELECT * FROM USERS WHERE ID = ${id};
    """.stripMargin.map(rs => User(rs.int("ID"),
      rs.string("NAME"),
      rs.string("CITY"),
      rs.int("AGE"),
      rs.boolean("SINGLE"))).list().apply().head

  def findAllSingle(single: Boolean):List[User] =
    sql"""
      |SELECT * FROM USERS WHERE SINGLE = ${single};
    """.stripMargin.map(rs => User(rs.int("ID"),
      rs.string("NAME"),
      rs.string("CITY"),
      rs.int("AGE"),
      rs.boolean("SINGLE"))).list().apply()

  def createUser(user: User):User =(???)

  def findAll():List[User] =
    sql"""
      |SELECT * FROM USERS;
    """.stripMargin.map(rs => User(rs.int("ID"),
      rs.string("NAME"),
      rs.string("CITY"),
      rs.int("AGE"),
      rs.boolean("SINGLE"))).list().apply()

}
