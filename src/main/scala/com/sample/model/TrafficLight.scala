package com.sample.model

/**
  * Created by lielran on 10/14/17.
  */
sealed trait TrafficLight

case object Green extends TrafficLight
case object Red extends TrafficLight
case object Yellow extends TrafficLight