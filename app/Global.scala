import com.google.inject.{AbstractModule, Guice}
import play.api.libs.json.Json
import play.api.mvc.{Results, RequestHeader}
import social.{GraphLoader, SocialGraph}
import scala.concurrent.Future

/**
 * Created by vince on 14/02/2015.
 */
object Global extends play.api.GlobalSettings with Results{

  private val injector = Guice.createInjector(new DefaultModule());

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)
  override def onHandlerNotFound(request: RequestHeader) = Future.successful( NotFound( Json.obj("reason" -> "route not found")))
  override def onBadRequest(request: RequestHeader, error: String) = Future.successful(BadRequest(Json.obj("reason" -> error)))
}

class DefaultModule extends AbstractModule {

  lazy val graph = GraphLoader.fromFile("facebook_combined.txt")

  override def configure() = {
    bind(classOf[SocialGraph]).toInstance(graph)
  }
}
