package social

import scala.collection.mutable

/**
 * Created by vince on 14/02/2015.
 */
trait SocialGraph {

  def path(id1: Int, id2: Int): Option[List[Int]]
  def commonFriends(id1: Int, id2: Int): Option[Set[Int]]
  def nodeSize: Int
  def edgeSize: Int
}

class DefaultSocialGraph(edges: List[(Int,Int)]) extends SocialGraph{

  lazy val graph = edges.foldRight( Map.empty[Int, Set[Int]]){ case ( (id1,id2) , g) =>
    g + (id1 -> ( g.getOrElse(id1, Set.empty[Int]) + id2) )
  }

  lazy val nodeSize = graph.size
  lazy val edgeSize = graph.map{ case (k, v) => v.size }.sum / 2

  def path(id1: Int, id2: Int): Option[List[Int]] = {

    bfs(id1, id2).map { parentMap =>
      var path: List[Int] = id2 :: Nil
      var parent = parentMap(id2)

      while(parent >= 0) {
        path = parent :: path
        parent = parentMap(parent)
      }

      path
    }
  }

  def commonFriends(id1: Int, id2: Int): Option[Set[Int]] = for {
    friends1 <- graph.get(id1)
    friends2 <- graph.get(id2)
  } yield friends1 & friends2
  
  def bfs(id1: Int, id2: Int): Option[mutable.Map[Int,Int]] = {

    if(graph.contains(id1) && graph.contains(id2)){
      val state = mutable.Set(id1)

      val q = mutable.Queue[Int]()
      q.enqueue(id1)

      val path = mutable.Map( id1 -> -1)

      while(!q.isEmpty) {
        val node = q.dequeue()

        for( edge <- graph(node) ){
          if( !state.contains(edge) ) {
            q.enqueue(edge)
            state += edge
            path += (edge -> node)

            if(edge == id2) {
              return Some( path )
            }
          }
        }
      }
    }; None
  }
}

object DefaultSocialGraph{
  def apply(edges: List[(Int,Int)]) = new DefaultSocialGraph( edges )
}
