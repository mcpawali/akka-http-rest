package reactore.entities

import java.sql.Date

import slick.driver.PostgresDriver


case class Employee(id: Long, firstName: String, lastName: String, dob: Date, isDeleted: Boolean = false, gender:String) extends BaseEntity

case class User(id: Long, userName: String, email: String, password: String, isDeleted: Boolean = false, employeeId:Long) extends BaseEntity

object Tables extends {
  val profile = PostgresDriver
} with Tables

trait Tables {
  val profile: PostgresDriver

  import profile.api._

  lazy val schema: profile.SchemaDescription = employeeTable.schema ++ userTable.schema

  class EmployeeTable(_tableTag: Tag) extends BaseTable[Employee](_tableTag, Some("employee"), "Employee") {
    def * = (id, firstName, lastName, dob, isDeleted, gender) <>(Employee.tupled, Employee.unapply)

    def ? = (Rep.Some(id), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(dob), Rep.Some(isDeleted), Rep.Some(gender)).shaped.<>({ r => import r._; _1.map(_ => Employee.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    override val id: Rep[Long] = column[Long]("EmployeeId", O.AutoInc, O.PrimaryKey)
    val firstName: Rep[String] = column[String]("FirstName", O.Length(150, varying = true))
    val lastName: Rep[String] = column[String]("LastName", O.Length(150, varying = true))
    val dob: Rep[java.sql.Date] = column[java.sql.Date]("DOB")
    override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
    val gender: Rep[String] = column[String]("Gender")
  }

  lazy val employeeTable = new TableQuery(tag => new EmployeeTable(tag))

  class UserTable(_tableTag: Tag) extends BaseTable[User](_tableTag, Some("employee"), "User") {
    def * = (id, userName, email, password, isDeleted, employeeId) <>(User.tupled, User.unapply)

    def ? = (Rep.Some(id), Rep.Some(userName), Rep.Some(email), Rep.Some(password), Rep.Some(isDeleted), Rep.Some(employeeId)).shaped.<>({ r => import r._; _1.map(_ => User.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    override val id: Rep[Long] = column[Long]("UserId", O.AutoInc, O.PrimaryKey)
    val userName: Rep[String] = column[String]("UserName", O.Length(150, varying = true))
    val email: Rep[String] = column[String]("Email", O.Length(150, varying = true))
    val password: Rep[String] = column[String]("Password", O.Length(150, varying = true))
    override val isDeleted: Rep[Boolean] = column[Boolean]("IsDeleted", O.Default(false))
    val employeeId : Rep[Long] = column[Long]("EmployeeId")
  }

  lazy val userTable = new TableQuery(tag => new UserTable(tag))
}