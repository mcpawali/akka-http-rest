package reactore.rest

import reactore.core.BaseRest
import reactore.entities.Tables.UserTable
import reactore.entities.User
import reactore.repository.UserRepository


object UserRestApi extends BaseRest[UserTable, User]("users", new UserRepository)