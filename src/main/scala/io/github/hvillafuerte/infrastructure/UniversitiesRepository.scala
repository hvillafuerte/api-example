package io.github.hvillafuerte.infrastructure

import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import scalikejdbc.{AutoSession, ConnectionPool,_}


class UniversitiesRepository {

  private val config: Config = ConfigFactory.load().getConfig("infrastructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session = AutoSession


  def findById(id: Int): University =
    sql"SELECT * FROM UNIVERSITIES WHERE ID = ${id};"
      .map(mapToUniversity)
      .list()
      .apply()
      .head

  def findByCity(city: String): List[University] =
    sql"SELECT * FROM UNIVERSIIES WHERE CITY = ${city};"
      .map(mapToUniversity)
      .list()
      .apply()

  def findAllOnline(online: Boolean): List[University] =
    sql"SELECT * FROM UNIVERSITIES WHERE ONLINE = ${online};"
      .map(mapToUniversity)
      .list()
      .apply()

  def findAll(): List[University] =
    sql"SELECT * FROM UNIVERSITIES;"
      .map(mapToUniversity)
      .list()
      .apply()

  def mapToUniversity(rs: WrappedResultSet): University =
    University(rs.int("ID"),
      rs.string("UNIVERSITY_NAME"),
      rs.string("CITY"),
      rs.int("UNIVERSITY_YEAR"),
      rs.boolean("ONLINE"))

}
