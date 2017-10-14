import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorPath, ActorRef, ActorSelection, ActorSystem, Identify, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Created by lielran on 10/14/17.
  */
class SimulatorController extends Actor with ActorLogging {

  private val akkaSystem = ActorSystem("AkkaSystem")
  private val initPeriod = 5.seconds
  private val intervalForPriorChange = 25.seconds
  private val intervalForChange = 30.seconds


  override def preStart(): Unit = {
    println("in preStart")
    init().map { ref =>

      akkaSystem.scheduler.schedule(initPeriod, intervalForPriorChange, ref, PriorChangeLightEvent)
      akkaSystem.scheduler.schedule(initPeriod + (intervalForChange - intervalForPriorChange), intervalForChange, ref, ChangeLightEvent)
    }
  }

  private def init(): List[ActorRef] = {
    initState match {
      case (startWithGreen: List[Direction], startWithRed: List[Direction]) =>
        val greens = startWithGreen.map { direction =>
          context.actorOf(Props(new TrafficLightActor(Green)), name = s"$TrafficLightActor$direction")
        }
        val reds = startWithRed.map { direction =>
          context.actorOf(Props(new TrafficLightActor(Red)), name = s"$TrafficLightActor$direction")
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
    // when the greeter is done, stop this actor and with it the application
    case z => println(s"got something else $z")
  }
}


case object ChangeLightEvent

case object PriorChangeLightEvent

