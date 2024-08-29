package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.users._
import scala.util.{Try,Success,Failure}
import scala.concurrent.{ExecutionContext,Future}
@Singleton
class LandingController @Inject()(val controllerComponents: ControllerComponents,users:UsersRepository,auth:AuthenticationRepo)
(using ec: ExecutionContext) 
extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    val user_id = request.session.get("user_id")
    user_id match {
      case Some(a) =>
        // Ok(views.html.landing())
        Redirect("/lists")
      case None =>  
        Redirect("/").flashing("Message" -> "Please Login First")

    }
  }
  def lists() = Action{ 
    Ok(views.html.lists(Nil))
  }

  def logout() = Action{
    Redirect("/").withNewSession
  }

  

}
