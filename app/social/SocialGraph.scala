package social

import scala.annotation.tailrec
import scala.collection.mutable

/**
 * Created by vince on 14/02/2015.
 */
trait SocialGraph {

  def path(id1: Int, id2: Int): List[Int]
  def commonFriends(id1: Int, id2: Int): Set[Int]
  def nodeSize: Int
  def edgeSize: Int
}

class DefaultSocialGraph(edges: List[(Int,Int)]) extends SocialGraph{

  lazy val graph = edges.foldRight( Map.empty[Int, Set[Int]]){ case ( (id1,id2) , g) =>
    g + (id1 -> ( g.getOrElse(id1, Set.empty[Int]) + id2) )
  }

  lazy val nodeSize = graph.size
  lazy val edgeSize = graph.map{ case (k, v) => v.size }.sum / 2

  def path(id1: Int, id2: Int): List[Int] = {
    if(id1 == id2) List(id1)
    else bfs(id1, id2)
  }

  def commonFriends(id1: Int, id2: Int): Set[Int] = {
    val cfriends = for {
      friends1 <- graph.get(id1)
      friends2 <- graph.get(id2)
    } yield friends1 & friends2

    cfriends.getOrElse(Set.empty[Int])
  }

  @tailrec
  private def _parent(id: Int, parents: Map[Int, Int], path: List[Int]): List[Int] = {
    if(id < 0) path
    else _parent(parents(id), parents, id :: path )
  }

  /**
   * breadth first search returns shortest path or Nil if not connected
   */
  def bfs(id1: Int, id2: Int): List[Int] = {

    if(graph.contains(id1) && graph.contains(id2)){
      val discovered = mutable.Set(id1)

      val q = mutable.Queue[Int]()
      q.enqueue(id1)

      var parents = Map( id1 -> -1 )

      while(q.nonEmpty) {
        val node = q.dequeue()

        for( edge <- graph(node) ){
          if( !discovered.contains(edge) ) {
            q.enqueue(edge)
            discovered += edge
            parents = parents + (edge -> node)

            if(edge == id2) {
              return _parent(id2, parents, Nil )
            }
          }
        }
      }
    }; Nil
  }
}

object DefaultSocialGraph{
  def apply(edges: List[(Int,Int)]) = new DefaultSocialGraph( edges )
}
