package models.users
import java.time.Instant
import java.util.UUID
import play.api.libs.json._
import java.net.InetAddress
case class UsersAuth(
	isSuccess:Boolean,
	idUser:UUID,
	attemptAt:Instant = Instant.now(),
	ip:String = InetAddress.getLocalHost().getHostAddress()

)

object UsersAuth:
	given Format[UsersAuth] = Json.format[UsersAuth]

