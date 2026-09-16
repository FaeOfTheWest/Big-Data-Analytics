object Assignment1 {

  // Part 3
  def weeklyCost(dailyCost: Double): Double = {
    dailyCost * 7
  }

  def main(args: Array[String]): Unit = {

    //Part 1
    val name: String = "Fae"
    var computerHours: Double = 8
    var phoneHours: Double = 3.0
    var dailyCost: Double = 2.50
    
    println("Personal Energy Usage")
    println(s"Name: $name")
    println(s"Computer Usage: $computerHours hours/day")
    println(s"Phone Usage: $phoneHours hours/day")
    println(s"Daily Cost: ${dailyCost}")
    println()

    //Part 2
    val usageLevel: String = if (dailyCost < 2.0) {
      "Low"
    } else if (dailyCost >= 2.0 && dailyCost <= 5.0) {
      "Moderate"
    } else {
      "High"
    }

    println("Usage Level Decision")
    println(s"Daily Cost: ${dailyCost}")
    println(s"Usage Level: $usageLevel")
    println()

    //Also Part 3
    val weekly: Double = weeklyCost(dailyCost)
    println("Weekly Cost")
    println(s"Daily Cost: ${dailyCost}")
    println(f"Weekly Cost: $weekly%.2f")
    println()

    //Part 4
    println("Daily Cost Breakdown (Day 1 - Day 7)")
    for (day <- 1 to 7) {
      println(f"Day $day: $dailyCost%.2f")
    }
    println()

    //Part 5
    val costs = List(2.5, 3.0, 4.0, 3.5, 5.0)
    val increasedCosts = costs.map(cost => cost * 1.10)

    println("Electricity Costs After 10% Increase")
    println(s"Original Costs: $costs")
    println(s"Updated Costs (10% increase): $increasedCosts")
  }
}
 