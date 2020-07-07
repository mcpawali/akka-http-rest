package reactore.rest

import reactore.core.BaseRest
import reactore.entities.Employee
import reactore.entities.Tables.EmployeeTable
import reactore.repository.EmployeeRepository

object EmployeeRestApi extends BaseRest[EmployeeTable, Employee]("employees", new EmployeeRepository) {
  val moreRoutes = path("employees" / "count"){
    complete("call the repository method here to get the count")
  } ~ route
}