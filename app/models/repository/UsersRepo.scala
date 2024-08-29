package models.users

import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import scala.concurrent.{ Future, ExecutionContext }
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID


@Singleton
class UsersRepository @Inject()(dcp:DatabaseConfigProvider,auth:AuthenticationRepo)(using ec:ExecutionContext){
	val dbConfig = dcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class UsersTable(tag:Tag) extends Table[Users](tag,"USERS"){
		def id = column[UUID]("ID",O.PrimaryKey,O.Default(UUID.randomUUID))
		def username = column[String]("USERNAME")
		def password = column[String]("PASSWORD")

		def * = (username,password,id).mapTo[Users]
	}

	def users = TableQuery[UsersTable]

	def add(username:String,password:String) =	{
		val query = users += Users(username,BCrypt.hashpw(password, BCrypt.gensalt()));
		db.run(query)
	}
	def get(username:String,password:String):Future[Option[Users]]=
		val query = 
			users  
			.filter(u => u.username === username).result
			.map(_.headOption.filter(u=>BCrypt.checkpw(password,u.password)))
		
		db.run(query)

	def get(username:String):Future[Option[Users]]=
		val query = users  .filter(u => u.username === username).result.headOption	
		db.run(query)
 	
 


}