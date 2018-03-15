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
val cexFilePath:String = "WoN1.cex"
val lines = Source.fromFile(htmlFilePath).getLines.toList

val matcher = """^:?[^\t]+\t""".r
val match0 = """^[^:]+:""".r
val match1 = """^:[^:\t]+""".r
val match2 = """^::[^:\t]+""".r
val match3 = """^:::[^:\t]+""".r

val contentMatch = """\t.+""".r

var cite0:String = ""
var cite1:String = ""
var cite2:String = ""
var cite3:String = ""

var counter:Int = 0

// Write it to disk
def writeCEX(cex:String):Unit = {
	val pw = new PrintWriter(new File(cexFilePath))
	pw.write(cex)
	pw.close
}

var cexString:String = ""

for (l <- lines){

  val textContentList:List[scala.util.matching.Regex.Match] = contentMatch.findAllMatchIn(l).toList
  val textContent:String = {
      if(textContentList.size > 0){
          textContentList(0).toString
      } else {
          ""
      }
  }
  val abbrevText:String = textContent.take(10)

  val gotZero:List[scala.util.matching.Regex.Match] = match0.findAllMatchIn(l).toList
  if (gotZero.size > 0){
      cite0 = gotZero(0).toString.split(":")(0)
      //println(cite0)
      counter = counter + 1
  }
  val gotOne:List[scala.util.matching.Regex.Match] = match1.findAllMatchIn(l).toList
  if (gotOne.size > 0){
      cite1 = gotOne(0).toString.split(":")(1)
      //println(s"${cite0}.${cite1}")
      counter = counter + 1
  }
  val gotTwo:List[scala.util.matching.Regex.Match] = match2.findAllMatchIn(l).toList
  if (gotTwo.size > 0){
      cite2 = gotTwo(0).toString.split(":")(2)
      cexString = cexString + (s"${urnBase}${cite0}.${cite1}.${cite2}#${abbrevText.trim}\n")
      counter = counter + 1
  }
  val gotThree:List[scala.util.matching.Regex.Match] = match3.findAllMatchIn(l).toList
  if (gotThree.size > 0){
      cite3 = gotThree(0).toString.split(":")(3)
      cexString = cexString + (s"${urnBase}${cite0}.${cite1}.${cite3}#${abbrevText.trim}\n")
      counter = counter + 1
  }
}

println(cexString)
writeCEX(cexString)
println(s"--------- ${counter} / ${lines.size} lines processed ------")
