package io.github.hvillafuerte.infrastructure

import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.TeachersBusinessLogic.Teacher
import scalikejdbc.{AutoSession, ConnectionPool, _}

class TeachersRepository {

  private val config: Config = ConfigFactory.load().getConfig("infrastructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session: AutoSession.type = AutoSession

  def findById(id: Int): Teacher=
    sql"SELECT * FROM TEACHERS WHERE ID = ${id};"
      .map(mapToTeacher)
      .list()
      .apply()
      .head

  def findAll():List[Teacher]=
    sql"SELECT * FROM TEACHERS;"
      .map(mapToTeacher)
      .list()
      .apply()

  def createTeacher(teacher: Teacher): Long=
    sql"""
      |INSERT INTO TEACHERS(ID,TEACHER_NAME,SUBJECT,CITY)
      |VALUES (${teacher.idTeacher},${teacher.name},${teacher.subject},${teacher.city});
    """.stripMargin
       .map(mapToTeacher)
       .updateAndReturnGeneratedKey()
       .apply()

  def mapToTeacher(rs: WrappedResultSet): Teacher =
    Teacher(rs.int("ID"),
        rs.string("TEACHER_NAME"),
        rs.string("SUBJECT"),
        rs.string("CITY"))
}
