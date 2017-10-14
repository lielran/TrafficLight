package com.sample.model

/**
  * Created by lielran on 10/14/17.
  */
sealed trait Direction

case object South extends Direction
case object North extends Direction
case object East extends Direction
case object West extends Direction


object IntersectionFactory {

  def getDirections() = {
    List(South, North, East, West)
  }

}
