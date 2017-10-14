
/**
  * Created by lielran on 10/14/17.
  */
class IntersectionFactoryTest extends org.specs2.mutable.Specification {
  "IntersectionFactory" should {
    "contain four directions" in {
      IntersectionFactory.getDirections() must have size 4
    }
    "contains all of the given traffic light directions'" in {
      IntersectionFactory.getDirections() mustEqual List(South, East, West, North)
    }

  }
}
