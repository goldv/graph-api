package social

/**
 * Created by vince on 14/02/2015.
 */
trait SocialGraph {
  def distance(id1: Int, id2: Int): Int
  def commonFriends(id1: Int, id2: Int): List[Int]
  def nodeSize: Int
  def edgeSize: Int
}

class DefaultSocialGraph(edges: List[(Int,Int)]) extends SocialGraph{

  lazy val graph = edges.foldRight( Map.empty[Int, Set[Int]]){ case ( (id1,id2) , g) =>
    g + (id1 -> ( g.getOrElse(id1, Set.empty[Int]) + id2) )
  }

  lazy val nodeSize = graph.size
  lazy val edgeSize = graph.map{ case (k, v) => v.size }.sum / 2

  def distance(id1: Int, id2: Int): Int = {
    0
  }

  def commonFriends(id1: Int, ind2: Int): List[Int] = {
    Nil
  }

}

object DefaultSocialGraph{
  def apply(edges: List[(Int,Int)]) = new DefaultSocialGraph( edges )
}
