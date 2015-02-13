package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}


/**
 * Created by vince on 10/10/2014.
 */
object Controller extends Controller{


  def getDistance(id1: Long, ids2: Long) = Action{
    Ok(Json.obj("result" -> "distance"))
  }

  def getCommonFriends(id1: Long, id2: Long) = Action{
    Ok(Json.obj("result" -> "common"))
  }


}
