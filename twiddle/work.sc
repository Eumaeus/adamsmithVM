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


val urnBase:String = "urn:cts:fufolio:adamsmith.won.fu2018:"
assert(CtsUrn(urnBase).fullyValid)


val htmlFilePath:String = "../working/WoN_1.html"
val lines = Source.fromFile(htmlFilePath).getLines.toList

val matcher = """^:?[^\t]+\t""".r
val match0 = """^[^:]+:""".r
val match1 = """^:[^:\t]+""".r

var cite0:String = ""
var cite1:String = ""
var cite2:String = ""
var cite3:String = ""

for (l <- lines){
  val gotZero:List[scala.util.matching.Regex.Match] = match0.findAllMatchIn(l).toList
  if (gotZero.size > 0){
      cite0 = gotZero(0).toString.split(":")(0)
      println(cite0)
  }
  val gotOne:List[scala.util.matching.Regex.Match] = match1.findAllMatchIn(l).toList
  if (gotOne.size > 0){
      cite1 = gotOne(0).toString.split(":")(1)
      println(s".${cite1}")
  }
}
