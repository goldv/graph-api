package controllers

import com.google.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import social.SocialGraph

/**
 * Created by vince on 10/10/2014.
 */
class SocialGraphController @Inject()(graph: SocialGraph) extends Controller{

  def getDistance(id1: Int, id2: Int) = Action{
    graph.path(id1,id2).map{ path =>
      Ok( Json.obj("distance" -> path.length, "possible_path" -> path))
    } getOrElse NotFound( Json.obj("reason" -> "there is no path possible between the two nodes given") )
  }

  def getCommonFriends(id1: Int, id2: Int) = Action{
    graph.commonFriends(id1,id2).map{ commonFriends =>
      Ok( Json.obj("common_friends" -> commonFriends) )
    } getOrElse  NotFound( Json.obj("reason" -> "no common friends found for the two nodes given") )
  }
}
