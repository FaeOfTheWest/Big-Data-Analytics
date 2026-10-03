import org.apache.spark.rdd.RDD

println("==========Assignment 2==========")

val data = spark.read.option("header", "true").csv("Student_data.csv").rdd
println("Student Data Loaded")
println("Total Lines (including header): " + data.count())
println("Header: ")
println(data.first())

// Remove header
val header = data.first()
val noheaderdata = data.filter(row => row != header)

println("\nFirst 5 rows of data (no headers):")
noheaderdata.take(5).foreach(println)

// Parse Row objects into tuples - wrap in parentheses
val studentRDD = (noheaderdata
  .filter(row => {
    try {
      row.getString(0).trim.nonEmpty &&
      row.getString(1).trim.nonEmpty &&
      row.getString(2).trim.nonEmpty &&
      row.getString(3).trim.nonEmpty
    } catch {
      case _: Exception => false
    }
  })
  .map(row => (
    row.getString(0).trim.toInt,
    row.getString(1).trim,
    row.getString(2).trim.toInt,
    row.getString(3).trim
  )))

println("\nParsed Student Records (First 3):")
studentRDD.take(3).foreach(println)

// Passing students
val passingStudents = studentRDD.filter(student => student._4 == "Pass")
println("\nPassing Student Records:")
passingStudents.collect().foreach(println)
println("Total passing students: " + passingStudents.count())

// Department counts
val departmentCounts = studentRDD
  .map(student => (student._2, 1))
  .reduceByKey(_ + _)
  .sortByKey()

println("\nStudents by Department:")
departmentCounts.collect().foreach(pair => 
  println(s"${pair._1}: ${pair._2}")
)

// Overall performance
val totalScore = studentRDD.map(student => student._3).sum()
val totalStudents = studentRDD.count()
val averageScore = totalScore / totalStudents

println(s"\nTotal Score: $totalScore")
println(s"Total Students: $totalStudents")
println(s"Average Score: $averageScore")