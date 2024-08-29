package models.users
import play.api.libs.json._
import java.util.UUID

case class Users(
	username:String,
	password:String,
	id:UUID = UUID.randomUUID
)
object Users:
	given Writes[Users] = new Writes{
		def writes(user:Users) = Json.obj(
			"id" -> user.id,
			"username" -> user.username
		)
	}
	given Reads[Users] = new Reads{
		def reads(json:JsValue)= for {
			username <- (json \ "username").validate[String]
			password <- (json \ "password").validate[String]
		}yield Users(username,password)
	}
