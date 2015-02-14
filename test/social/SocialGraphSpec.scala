package social

import org.specs2.mutable._

/**
 * Created by vince on 14/02/2015.
 */
class SocialGraphSpec extends Specification{

  val g = GraphLoader.fromFile("facebook_combined.txt")

  "social graph" should{
    "have 4039 nodes" in{
      g.nodeSize must be equalTo 4039
    }
    "have 88,234 edges" in{
      g.edgeSize must be equalTo 88234
    }
    "show that 4017 & 3982 have the friends 3980,3986,4014,4021,4026,4030] in common" in{
      g.commonFriends(4017, 3982) must be equalTo Set(3980,3986,4014,4021,4026,4030)
    }
    "show that 3982 & 4017 have the friends 3980,3986,4014,4021,4026,4030] in common" in{
      g.commonFriends(3982, 4017) must be equalTo Set(3980,3986,4014,4021,4026,4030)
    }
    "show no common friends for out of range id" in{
      g.commonFriends(5000, 0) must beEmpty
    }
    "have a distance of 3 between 3982 & 4017" in{
      val path = g.path(3982, 4017)

      path.length must be equalTo 3
    }
    "have a distance of 3 between 4017 & 3982" in{
      val path = g.path(4017, 3982)

      path.length must be equalTo 3
    }
    "have a distance of 2 between 4017 & 4017" in{
      val path = g.path(4017, 4017)

      path.length must be equalTo 1
    }
    "show no possible path for nodes 20 & 5000" in{
      val path = g.path(20, 5000)
      path must beEmpty
    }
  }

}
