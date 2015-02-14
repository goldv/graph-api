package social

import org.specs2.mutable._

/**
 * Created by vince on 14/02/2015.
 */
class SocialGraphSpec extends Specification{

  "social graph " should{
    "have 4039 nodes" in{
      val g = GraphLoader.fromFile("facebook_combined.txt")
      g.nodeSize must beEqualTo(4039)
    }
    "have 88,234 edges" in{
      val g = GraphLoader.fromFile("facebook_combined.txt")
      g.edgeSize must beEqualTo(88234)
    }
  }

}
