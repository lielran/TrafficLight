package com.sample

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import com.sample.events.{ChangeLightEvent, PriorChangeLightEvent}
import com.sample.model._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by lielran on 10/14/17.
  */
class SimulatorController extends Actor with ActorLogging {

  private val akkaSystem = ActorSystem("AkkaSystem")
  private val initPeriod = 5.seconds
  private val intervalForPriorChange = 250.seconds
  private val intervalForChange = 300.seconds


  override def preStart(): Unit = {
    init().map { ref =>
      akkaSystem.scheduler.schedule(initPeriod, intervalForPriorChange, ref, PriorChangeLightEvent)
      akkaSystem.scheduler.schedule(initPeriod + (intervalForChange - intervalForPriorChange), intervalForChange, ref, ChangeLightEvent)
    }
  }

  private def init(): List[ActorRef] = {
    initState match {
      case (startWithGreen: List[Direction], startWithRed: List[Direction]) =>
        val greens = startWithGreen.map { direction =>
          context.actorOf(Props(new TrafficLightActor(new TrafficLightDao(Green))), name = s"$TrafficLightActor$direction")
        }
        val reds = startWithRed.map { direction =>
          context.actorOf(Props(new TrafficLightActor(new TrafficLightDao(Red))), name = s"$TrafficLightActor$direction")
        }
        greens ::: reds
    }
  }

  /**
    * can be change to other condition, right now just make sure we start without conflict
    *
    * @return
    */
  private def initState = {
    IntersectionFactory.getDirections().partition(d => South == d || North == d)
  }

  def receive: PartialFunction[Any, Unit] = {
    case _=> log.error(s"should not get any messages")
  }
}
