import scala.concurrent.Future

/**
  * Created by lielran on 10/14/17.
  */
class TrafficLightDao(state: TrafficLight) {


  private var db: DisplayLights = DisplayLights(state)

  def getCurrentState(): Future[DisplayLights] = Future.successful(db)

  def save(update: DisplayLights): Future[DisplayLights] = {
    db = update
    Future.successful(update)
  }


}
