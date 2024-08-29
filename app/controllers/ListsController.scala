package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.util.{Try,Success,Failure}
import scala.concurrent.{ExecutionContext,Future}
import java.util.UUID
import java.time.LocalDateTime
import models.lists._
@Singleton
class ListController @Inject()(val controllerComponents: ControllerComponents)(val lists: ListsRepo)
(using ec: ExecutionContext) 
extends BaseController {

  def index() = Action.async { request =>
    val user_id = request.session.get("user_id").get.toUuid
    lists.get(user_id)
    .map{l=>
      Ok(views.html.lists(l.toList))
    }    
  }

  def hidden() = Action.async { request =>
    val user_id = request.session.get("user_id").get.toUuid
    lists.get(user_id)
    .map{l=>
      Ok(views.html.hidden(l.toList))
    }    
  }

  def add() = Action(parse.json).async{ implicit request =>
    val user_id = request.session.get("user_id").get.toUuid
    request.body.validate[Lists].map{ list => 
      lists.add(user_id,list.task,list.status,list.description,list.due).map( i => 
        Ok(Json.obj("Message" -> "Added"))
      )
    }.getOrElse{
      Future(BadRequest)
    }
  }

  def getAll() = Action.async{ implicit request =>
    val user_id = request.session.get("user_id").get.toUuid
    lists.get(user_id)
    .map(l=> Ok(Json.toJson(l)))
  }

  def getById = Action(parse.json).async{ request =>
    val id = (request.body \ "id_task").as[Int]
    lists.get(id)
    .map(l => Ok(Json.toJson(l)))
  }

  def get(status:String) = Action.async{ request => 
    val user_id = request.session.get("user_id").get.toUuid
    if(List("onwork","on-work","pending","done") contains status)
      lists.get(user_id,status.toUpperCase)
      .map(lists=> Ok(Json.toJson(lists)))
    else 
      Future(BadRequest(Json.obj("Message" -> "Enter a valid url brother")))
  }


  def update(id:Int) = Action(parse.json).async{ request =>
    val user_id = request.session.get("user_id").get.toUuid
    request.body.validate[Lists].map{ list => 
      lists.update(user_id,id,list.task,list.status,list.description,list.due)
      .map(l => 
        Ok(Json.obj("Message" -> "Success")))
    }.getOrElse{
      Future(BadRequest)
    }
  }

  def updateStatus(id:Int,status:String) = Action.async{ request =>
    lists.updateStatus(id,status).map(l=> Ok(Json.obj("Message" -> "Success")))
  }

  def updateVisibility(id:Int,bool:Boolean) = Action.async{request=>
    lists.hideUnhide(id,bool).map(l=> 
      println(bool)
      Ok(Json.obj("Message" -> "Success")))
  }

  def delete(id:Int) = Action.async{ request =>
    lists.delete(id).map(l=> Ok(Json.obj("Message" -> "Success")))
  }

 


}
