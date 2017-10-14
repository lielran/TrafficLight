package com.sample

import akka.actor.{ActorSystem, _}
import akka.testkit.{ImplicitSender, TestKit}
import com.sample.events.{ChangeLightEvent, PriorChangeLightEvent}
import com.sample.model.{Green, Red, Yellow}
import org.specs2.specification.After

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by lielran on 10/14/17.
  */
class TrafficLightActorTest extends org.specs2.mutable.Specification {

  private val timeout = 5.seconds


  "A TrafficLight" should {
    "change from red to green" in new AkkaTestkitSpecs2Support {
      within(timeout) {
        //Given
        val dao = new TrafficLightDao(Red)
        val props = Props(new TrafficLightActor(dao))
        val actor =system.actorOf(props)

        //When
        actor ! ChangeLightEvent


        Thread.sleep(5)
        //Then
        Await.result(dao.getCurrentState(), timeout) mustEqual DisplayLights(List(Green))


        //When
        actor ! PriorChangeLightEvent

        //Then
        Await.result(dao.getCurrentState(), timeout) mustEqual DisplayLights(List(Green, Yellow))

        //When
        actor ! ChangeLightEvent

        //Then
        Await.result(dao.getCurrentState(), timeout) mustEqual DisplayLights(List(Red))

      }
    }


  }


}

/**
  * credit http://blog.xebia.com/testing-akka-with-specs2/
  */
/* A tiny class that can be used as a Specs2 'context'. */
abstract class AkkaTestkitSpecs2Support extends TestKit(ActorSystem())
  with After
  with ImplicitSender {
  // make sure we shut down the actor system after all tests have run
  def after = TestKit.shutdownActorSystem(system)
}