package reactore.repository

import reactore.core
import reactore.core.BaseRepository
import reactore.entities.Tables.{EmployeeTable, UserTable}
import reactore.entities.{Employee, User}
import slick.lifted.TableQuery

import scala.concurrent.Future
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

/** Implementations of repositories **/

case class UserDetails(employeeId: Long, userName: String, firstName: String, lastName: String)

class EmployeeRepository extends BaseRepository[EmployeeTable, Employee](TableQuery[EmployeeTable])

class UserRepository extends BaseRepository[UserTable, User](TableQuery[UserTable]) {
  val empRepo = new EmployeeRepository

  override def getById(id: Long): Future[Option[User]] = {
    val superRes = super.getById(id)
    //remove the password field with some dummy data while sending back
    superRes.map(_.map(_.copy(password = "*****")))
  }

  def getUserDetails: Future[Seq[UserDetails]] = {
    val joinQuery                              = getAllQuery.join(empRepo.getAllQuery).on(_.employeeId === _.id)
    val joinRes: Future[Seq[(User, Employee)]] = db.run(joinQuery.result)
    joinRes map { tupleList =>
      tupleList.map { tuple =>
        UserDetails(tuple._2.id, tuple._1.userName, tuple._2.firstName, tuple._2.lastName)
      }
    }
  }
}