package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.users._
import scala.util.{Try,Success,Failure}
import scala.concurrent.{ExecutionContext,Future}
import java.time.LocalDateTime
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,users:UsersRepository,auth:AuthenticationRepo)
(using ec: ExecutionContext) 
extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>

    val user_id = request.session.get("user_id")
    user_id match {
      case Some(a) =>
        Redirect("/home")
      case None =>  
        Ok(views.html.index())
    }
  }

  def register() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register())
  }
  
  def get() = Action(parse.json).async{ request=>
    request.body.validate[Users].map( user=>
      users.get(user.username,user.password).map( user=>
        user match {
          case Some(user) => 
            auth.authenticate(user.id,true)
            Ok(Json.obj("Message" -> "Success")).withSession("user_id" -> user.id.toString)
          case None =>
            Ok(Json.obj("Message" -> "Not Found"))
        }
      )

    ).getOrElse{
      Future(BadRequest)
    }

  }
  def addUser() = Action(parse.json).async{ request =>
    request.body.validate[Users].map( user =>
      users.get(user.username)
      .flatMap{ userRes => 
        userRes match {
          case Some(u) => 
            println(u)
            Future(Ok(Json.obj("Message" -> s"Username Invalid")))

          case None =>
            users.add(user.username,user.password).map( u=>
              Ok(Json.obj("Message" -> "Success"))
            )
        }

      }
      
    ).getOrElse{
      Future(BadRequest)
    }

  }
}
