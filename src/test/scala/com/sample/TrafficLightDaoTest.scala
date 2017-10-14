package com.sample


import com.sample.model.{Red, Yellow}
import org.specs2.matcher.FutureMatchers

import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Created by lielran on 10/14/17.
  */
class TrafficLightDaoTest extends org.specs2.mutable.Specification with FutureMatchers  {

  val dao = new TrafficLightDao(Red)
  private val timeout = 5.seconds

  "dao" should{
    "save"  in {
      val input = DisplayLights(Yellow)
      Await.result(dao.save(input), timeout) mustEqual input
    }

    "get current" in {
      val input = DisplayLights(Yellow)
      Await.result(dao.save(input), timeout)
      Await.result(dao.getCurrentState(), timeout) mustEqual input
    }
  }


}



