package models.users
import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import scala.concurrent.{ExecutionContext}
import java.util.UUID
import java.time.Instant
@Singleton
class AuthenticationRepo @Inject()(val dcp:DatabaseConfigProvider)(using ec:ExecutionContext){
	val dbConfig = dcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class AuthTable(tag:Tag) extends Table[UsersAuth](tag,"USERS_AUTH"){
		def idUser = column[UUID]("ID_USERS",O.PrimaryKey,O.Default(UUID.randomUUID))
		def isSuccess = column[Boolean]("IS_SUCCESS")
		def attemptAt = column[Instant]("ATTEMPT_AT")
		def ip = column[String]("IP_ADDRESS")
		def * = (isSuccess,idUser,attemptAt,ip).mapTo[UsersAuth]
	}

	val auth = TableQuery[AuthTable]

	case class Meow(s:String)

	def authenticate(id:UUID,isSuccess:Boolean)={
		val query = auth += UsersAuth(isSuccess,id)
		db.run(query)
	}

}
