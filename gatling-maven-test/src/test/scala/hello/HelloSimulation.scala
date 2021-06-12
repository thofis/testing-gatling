package hello

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt

class HelloSimulation extends Simulation {
  val baseHttpProtocol: HttpProtocolBuilder =
    http.baseUrl("http://localhost:8080")

//  val random = new Random
//
//  val names: Seq[String] = Source
//    .fromInputStream(getClass.getClassLoader.getResourceAsStream("names.txt"))
//    .getLines().toSeq
//
//  def getRandomElement(seq: Seq[String], random: Random): String =
//    seq(random.nextInt(seq.length))

  val feeder = csv("names.csv").random

  val scn: ScenarioBuilder = scenario("simplest")
    .feed(feeder)
    .exec(
      http("hello")
        .get(s"/hello")
        .queryParam("${param}", "${value}")
    )

  setUp(
    scn.inject(rampUsers(1000).during(20.seconds))
  ).protocols(baseHttpProtocol)

}
