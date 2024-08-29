package models.lists

import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import scala.concurrent.{ Future, ExecutionContext }
import java.util.UUID
import java.time.LocalDateTime

@Singleton
class ListsRepo	@Inject()(val dcp:DatabaseConfigProvider)(using ec:ExecutionContext){
	val dbConfig = dcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class ListTable(tag:Tag) extends Table[Lists](tag, "LISTS"){
		val id = column[Int]("ID",O.PrimaryKey,O.AutoInc)
		val idUser = column[UUID]("ID_USER")
		val task = column[String]("TASK", O.Length(55))
		val description = column[String]("DESCRIPTION")
		val status = column[String]("STATUS")
		val due = column[LocalDateTime]("DUE")
		val isHidden = column[Boolean]("IS_HIDDEN",O.Default(false))

		def * = (task,status,description,due,isHidden,idUser,id).mapTo[Lists]

	}

	val lists = TableQuery[ListTable]
	// UUID.fromString(superSecretId)
	def add(idUser:UUID,task:String,status:String,description:String,due:LocalDateTime) = 
		val query = lists+= Lists(task,status,description,due,idUser=idUser)
		db.run(query)


	def get(idUser:UUID)=
		val query = lists.filter(_.idUser=== idUser).result
		db.run(query)

	def get(id:Int,isHidden:Boolean)=
		val query = lists.filter(l=> l.id === id && l.isHidden === isHidden).result
		db.run(query)

	def get(id:Int)=
		val query = lists.filter(_.id === id).result
		db.run(query)

	def get(idUser:UUID,status:String)=
		val query = lists.filter(l => l.idUser === idUser && l.status === status).result
		db.run(query)

	def update(idUser:UUID,id:Int,task:String,status:String,description:String,due:LocalDateTime) =
		val query = 
			lists.filter(l=> l.idUser === idUser && l.id === id)
			.map(l=> (l.task,l.status,l.description,l.due)).update((task,status,description,due))
		db.run(query)

	def hideUnhide(id:Int,bool:Boolean)=
		val query = lists.filter(_.id === id).map(_.isHidden).update(bool)
		db.run(query)

	def updateStatus(id:Int,status:String) =
		val query = lists.filter(_.id ===id).map(_.status).update(status.toUpperCase)
		db.run(query)

	def delete(id:Int) =
		val query = lists.filter(l=> l.id === id).delete
		db.run(query)

}






