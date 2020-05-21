package io.github.hvillafuerte.infrastructure

import com.typesafe.config.{Config, ConfigFactory}
import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import scalikejdbc.{AutoSession, ConnectionPool,_}

class SubjectsRepository {

  private val config: Config = ConfigFactory.load().getConfig("infrastructure.h2")
  Class.forName(config.getString("driver"))
  ConnectionPool.singleton(
    config.getString("url"),
    config.getString("user"),
    config.getString("pass")
  )

  implicit val session = AutoSession

  def findById(id: Int): Subject =
    sql"SELECT * FROM SUBJECTS WHERE ID = $id;"
      .stripMargin
      .map(mapToSubject)
      .list()
      .apply()
      .head

  def findApprovedSubject(approved: Boolean): List[Subject] =
    sql"SELECT * FROM SUBJECTS WHERE APPROVED_SUBJECT = $approved;"
      .stripMargin
      .map(mapToSubject)
      .list()
      .apply()

  def findAll(): List[Subject] =
    sql"SELECT * FROM SUBJECTS;"
      .stripMargin
      .map(mapToSubject)
      .list()
      .apply()

  def createSubject(subject: Subject): Long =
    sql"""
      |INSERT INTO SUBJECTS(ID, YEAR, SUBJECT_NAME, APPROVED_SUBJECT)
      |VALUES (${subject.id},${subject.year},${subject.subject_name},${subject.approvedSubject})
    """
      .stripMargin
      .map(mapToSubject)
      .updateAndReturnGeneratedKey()
      .apply()

  def mapToSubject(rs: WrappedResultSet): Subject =
    Subject(rs.int("ID"),
      rs.int("YEAR"),
      rs.string("SUBJECT_NAME"),
      rs.boolean("APPROVED_SUBJECT"))

}

