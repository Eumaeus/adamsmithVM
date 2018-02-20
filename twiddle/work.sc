import java.io._
import scala.io.Source
import edu.holycross.shot.cite._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.scm._
import cats.syntax.either._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import io.circe.parser.decode


val htmlFilePath:String = "../working/WoN_1.html"
val lines = Source.fromFile(htmlFilePath).getLines.toList

val matcher = """^:?[^\t]+\t""".r

for (l <- lines){
  val gotOne:List[scala.util.matching.Regex.Match] = matcher.findAllMatchIn(l).toList
  gotOne.size match {
    case 1 => println(gotOne(0))
    case _ => println(s"-------\n${l}\n-------")
  }

}
