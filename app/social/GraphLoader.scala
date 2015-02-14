package social

import org.slf4j.LoggerFactory
import scala.io.Source
import scala.util.Try

/**
 * Created by vince on 14/02/2015.
 */
object GraphLoader {
  
  val log = LoggerFactory.getLogger( GraphLoader.getClass )

  def fromFile(file: String) = {
    log.info(s"loading graph from file $file")

    val stream = getClass.getClassLoader.getResourceAsStream(s"$file")

    val edges = Source.fromInputStream(stream).getLines().toList.foldRight(List.empty[(Int,Int)] ){ (line, accu) =>
      processLine(line, accu)
    }

    DefaultSocialGraph(edges)
  }

  def processLine(line: String, accu: List[(Int,Int)]): List[(Int,Int)] = {
    val ids = line.split(" ")

    if(ids.length == 2){

      // undirected graph so add opposite edge
      val idseq = for{
        id1 <- Try(ids(0).toInt)
        id2 <- Try(ids(1).toInt)
      } yield (id1 -> id2) :: (id2 -> id1) :: accu

      idseq.getOrElse(accu)
    } else accu
  }

}
