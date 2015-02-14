package social

/**
 * Created by vince on 14/02/2015.
 */
object SocialGraphPerformanceTest extends App {

  val g = GraphLoader.fromFile("facebook_combined.txt")

  def bfs = {
    val start = System.currentTimeMillis()
    println(s"bfs start: ${start}")
    val results = for{
      i <- 1 to 100000
    } yield g.bfs(4017, 3982)
    val stop = System.currentTimeMillis()
    println(s"bfs stop: ${stop} took ${stop - start}")
    println(s"test complete for ${results.length} calls")
  }

  bfs
}
