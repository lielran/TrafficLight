
/**
  * Created by lielran on 10/14/17.
  */
object IntersectionFactory {

  def getDirections() = {
    List(South, North, East, West)
  }

}

sealed trait Direction

case object South extends Direction

case object East extends Direction

case object West extends Direction

case object North extends Direction