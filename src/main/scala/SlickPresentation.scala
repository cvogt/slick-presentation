import scala.reflect.ClassTag
import scala.slick.ast.{TypedType}
import scala.slick.lifted.FlatShapeLevel
import scala.slick.lifted.Shape
import scala.slick.collection.heterogenous._
trait Tables {
  implicit def stringColumnType: TypedType[String] = ???
  type BranchRow = HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HCons[String,HNil.type]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]

  implicitly[Shape[_ <: FlatShapeLevel, BranchRow, BranchRow, _]]
  def x = implicitly[Shape[_ <: FlatShapeLevel, HCons[String,HNil.type], _, _]]
  val i = x
}

