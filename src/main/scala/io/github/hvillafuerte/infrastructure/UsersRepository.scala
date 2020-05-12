package io.github.hvillafuerte.infrastructure


import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import io.github.hvillafuerte.application.UsersBusinessLogic.User
import scalikejdbc.{AutoSession, ConnectionPool}
import scalikejdbc._

object UsersRepository extends App {

  private val config: Config = ConfigFactory.load().getConfig("infraestructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session = AutoSession

  private val maps: List[Map[String, Any]]=
    sql"""
         SELECT * FROM USERS WHERE ID = 1;
       """.stripMargin.map(_.toMap()).list().apply()

  println(maps)

  Thread.sleep(Long.MaxValue)

}
