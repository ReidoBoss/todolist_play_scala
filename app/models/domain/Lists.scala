package models.lists

import play.api.libs.json._
import java.util.UUID
import java.time.LocalDateTime


extension(s:String)
	def toUuid = 
	  UUID.fromString(s);

case class Lists(
	task:String,
	status:String,
	description:String,
	due: LocalDateTime, 
	isHidden:Boolean = false,
	idUser:UUID = UUID.randomUUID,
	id:Int = 0
)
object Lists:
	given Reads[Lists] = new Reads{
		def reads(json:JsValue) = for{
			task <- (json \ "task").validate[String]
			status <- (json \ "status").validate[String]
			description <- (json \ "description").validate[String]
			due <- (json \"due").validate[LocalDateTime]
		}yield Lists(task,status,description,due)
	}
	given Writes[Lists] = new Writes{
		def writes(list:Lists)= Json.obj(
			"id_task" -> list.id,
			"task" -> list.task,
			"status" -> list.status,
			"description" -> list.description,
			"due" -> list.due,
			"isHidden" -> list.isHidden
		)

	}
	// given Format[Lists] = Json.format[Lists]	
