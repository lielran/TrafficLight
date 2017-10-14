package com.sample

import com.sample.model.{Green, Red, Yellow}
import org.specs2.mutable.Specification

/**
  * Created by lielran on 10/14/17.
  */
class DisplayLightsTest extends Specification {

  "com.sample.DisplayLights" should {
    "not in green mode" in {
      DisplayLights(Red).inGreenMode() mustEqual false
      DisplayLights().inGreenMode() mustEqual false
    }

    "in green mode" in {
      DisplayLights(Green).inGreenMode() mustEqual true
    }
    "must be contains" in {
      DisplayLights(List(Green, Yellow)).inGreenMode() mustEqual true
    }
  }

}
