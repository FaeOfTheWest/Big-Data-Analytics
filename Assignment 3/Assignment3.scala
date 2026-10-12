import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.functions.col

val students_data = spark.read.format("csv").option("header", "true").option("inferSchema", "true").load("Student.csv")

println("Part 1.2")
println("First 10 Students")
students_data.show(10)


println("Part 1.3")
println("Students Data Schema")
students_data.printSchema()


println("Part 1.4")
val totalStudents = students_data.count()
println("Total Number of Students: " + totalStudents)


println("Part 2.1")
val featureColumns = Array(
      "Study_Hours",
      "Attendance",
      "Assignments_Completed",
      "Previous_GPA",
      "Sleep_Hours",
      "Screen_Time"
    )

println("Part 2.2")
val assembler = new VectorAssembler().setInputCols(featureColumns).setOutputCol("features")

println("Part 2.3")
val labeledData = students_data.withColumnRenamed("Pass", "label").withColumn("label", col("label").cast("double"))
val preparedData = assembler.transform(labeledData)
preparedData.select("Student_ID", "features", "label").orderBy("Student_ID").show(10, truncate = false)

println("Part 3.1")
val classifier = new DecisionTreeClassifier().setLabelCol("label").setFeaturesCol("features").setMaxDepth(5).setSeed(42L)

println("Part 3.2")
val model = classifier.fit(preparedData)

println("Part 3.3")
println(model.toDebugString)
val predictions = model.transform(preparedData)
predictions.select("Student_ID", "label", "prediction").orderBy("Student_ID").show(10, truncate = false)

println("Part 4.1")
predictions.select(
    col("Student_ID"),
    col("label").alias("Actual_Pass"),
    col("prediction").alias("Predicted_Pass")
    ).orderBy("Student_ID").show(50, truncate = false)

val correctPredictions = predictions.filter(col("label") === col("prediction")).count()

val incorrectPredictions = predictions.filter(col("label") =!= col("prediction")).count()

println("Part 4.2")
println(s"Correct predictions = $correctPredictions")
println("Part 4.3")
println(s"Incorrect predictions = $incorrectPredictions")

val evaluator = {
  new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("accuracy")
}


val accuracy = evaluator.evaluate(predictions)

println("Part 5.1")
println(f"Accuracy = $accuracy%.6f")
println(f"Accuracy (%%) = ${accuracy * 100.0}%.2f")

println("Part 5.2")

val confusionMatrix = {
  predictions
    .groupBy("label")
    .pivot("prediction", Seq(0.0, 1.0))
    .count()
    .na.fill(0L)
    .withColumnRenamed("label", "Actual_label")
    .withColumnRenamed("0.0", "Predicted_0")
    .withColumnRenamed("1.0", "Predicted_1")
    .orderBy("Actual_label")
}

confusionMatrix.show(truncate = false)

println("Part 5.3")
println(model.featureImportances)

println("Part 5.4")
val importanceValues = model.featureImportances.toArray

featureColumns.zip(importanceValues).foreach {
      case (feature, importance) =>
        println(f"$feature%-24s $importance%.6f")
    }