package com.sample

import akka.actor.{Actor, ActorLogging, Props}
import com.sample.events.{ChangeLightEvent, PriorChangeLightEvent}
import com.sample.model.{Green, Red, TrafficLight, Yellow}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by lielran on 10/14/17.
  */

object TrafficLightActor {
  def props = {
    Props[TrafficLightActor]
  }

}

class TrafficLightActor(val dao: TrafficLightDao) extends Actor with ActorLogging {

  displayState()

  private val warningBeforeSwitchMode = DisplayLights(List(Green, Yellow))
  private val greenMode = DisplayLights(Green)
  private val redMode = DisplayLights(Red)

  private def changeState(): Unit = {
    dao.getCurrentState().flatMap{
      case green if green.inGreenMode() => dao.save(redMode)
      case _ => dao.save(greenMode)
    }.map { _=>
      displayState()
    }
  }

  private def warningBeforeChangeState(): Unit = {
    dao.getCurrentState().flatMap{
      case green if green.inGreenMode() => dao.save(warningBeforeSwitchMode).map { _=>
        displayState()
      }
      case _ => Future.successful()
    }
  }

  private def displayState() =dao.getCurrentState().map(s => log.info(s"[${this.self.path}]:state=${s.state}  "))


  override def receive: Receive = {
    case ChangeLightEvent => changeState()
    case PriorChangeLightEvent => warningBeforeChangeState()
    case unSupportedMessage => log.error(s"${this.self.path} got $unSupportedMessage")
  }
}




case class DisplayLights(state: List[TrafficLight] = List.empty) {
  def inGreenMode(): Boolean = state.contains(Green)
}

object DisplayLights {

  def apply(init: TrafficLight): DisplayLights = {
    DisplayLights(List(init))
  }


}