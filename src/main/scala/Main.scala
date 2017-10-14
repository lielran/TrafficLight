import akka.actor.ActorSystem

/**
  * Created by lielran on 10/14/17.
  */
object Main {

  def main(args: Array[String]): Unit = {
    println("------------Traffic Simulator--------------------")
    akka.Main.main(Array(classOf[SimulatorController].getName))
    println("-------------------------------------------------")

  }
}
