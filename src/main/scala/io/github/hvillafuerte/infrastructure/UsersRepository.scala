package io.github.hvillafuerte.infrastructure

import io.github.hvillafuerte.application.SubjectsBusinessLogic.Subject
import io.github.hvillafuerte.application.UniversitiesBusinessLogic.University
import io.github.hvillafuerte.application.UsersBusinessLogic.User
import scalikejdbc.{AutoSession, ConnectionPool}
import scalikejdbc._

object UsersRepository extends App {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:/tmp/henry", "user", "pass")

  implicit val session = AutoSession

  sql"""
       |DROP TABLE IF EXISTS USERS;
       |CREATE TABLE IF NOT EXISTS USERS (
       |  ID IDENTITY NOT NULL PRIMARY KEY,
       |  NAME VARCHAR(64) NOT NULL,
       |  CITY VARCHAR(64) NOT NULL,
       |  AGE INT NOT NULL,
       |  SINGLE BOOLEAN NOT NULL
       |)
       |""".stripMargin.execute.apply()
  sql"""
       |DROP TABLE IF EXISTS SUBJECTS;
       |CREATE TABLE IF NOT EXISTS SUBJECTS (
       |  ID IDENTITY NOT NULL PRIMARY KEY,
       |  YEAR INT NOT NULL,
       |  SUBJECT_NAME VARCHAR(64) NOT NULL,
       |  APPROVED_SUBJECT BOOLEAN NOT NULL
       |)
       |""".stripMargin.execute.apply()
  sql"""
        DROP TABLE IF EXISTS MARKS;
        CREATE TABLE IF NOT EXISTS MARKS(
        ID IDENTITY NOT NULL PRIMARY KEY,
        MARK INT NOT NULL,
        FECHA DATE NOT NULL
        )
        """.stripMargin.execute.apply()
  sql"""
       DROP TABLE IF EXISTS TEACHERS;
       CREATE TABLE IF NOT EXISTS TEACHERS(
       ID IDENTITY NOT NULL PRIMARY KEY,
       TEACHER_NAME VARCHAR (64) NOT NULL,
       SUBJECT VARCHAR (64) NOT NULL
       )
     """.stripMargin.execute.apply()

  sql"""
       DROP TABLE IF EXISTS UNIVERSITIES;
       CREATE TABLE IF NOT EXISTS UNIVERSITIES (
       ID IDENTITY AUTO_INCREMENT,
       UNIVERSITY_NAME VARCHAR (64) NOT NULL,
       CITY VARCHAR (64) NOT NULL,
       UNIVERSITY_YEAR INT NOT NULL,
       ONLINE BOOLEAN NOT NULL
       )
     """.stripMargin.execute.apply()

  def create(user: User): Long = {
    sql"""
         |INSERT INTO USERS(ID, NAME, CITY, AGE, SINGLE)
         |VALUES (${user.userId}, ${user.name}, ${user.city}, ${user.age}, ${user.single})
         |""".stripMargin
      .updateAndReturnGeneratedKey.apply()
  }

  def create(subject: Subject): Long = {
    sql"""
         |INSERT INTO SUBJECTS(ID, YEAR, SUBJECT_NAME, APPROVED_SUBJECT)
         |VALUES (${subject.id}, ${subject.year}, ${subject.subject_name}, ${subject.approvedSubject})
         |""".stripMargin
      .updateAndReturnGeneratedKey.apply()
  }

  def create(university: University): Long = {
    sql"""
          INSERT INTO UNIVERSITIES(ID,UNIVERSITY_NAME,CITY,UNIVERSITY_YEAR,ONLINE)
          VALUES (${university.id},${university.name},${university.city},${university.year},${university.online})
          """.stripMargin.updateAndReturnGeneratedKey.apply()
  }

  def update(user: User): Long = {
    sql"""
          UPDATE USERS
          SET SINGLE = ${user.single}
       """.stripMargin
      .update.apply()
  }

  def delete(subject: Subject): Long = {
    sql"""
        |DELETE FROM SUBJECTS WHERE APPROVED_SUBJECT = ${subject.approvedSubject}
      """.stripMargin.update.apply()
  }



  val henry = User(1,"Henry", "Sevilla", 18, true)
  val pepe = User(2,"Pepe", "Valencia", 23, false)
  val history = Subject(2,2019,"History",true)
  val ULR = University(8,"Universidad de La Rioja","La Rioja",2018,true )

  create(henry)
  create(pepe)
  create(history)
  update(henry)
  update(pepe)
  delete(history)


  Thread.sleep(Long.MaxValue)

}
