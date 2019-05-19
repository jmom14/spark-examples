package tutorial;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class RealState01 {
	
	public static void main(String[] args) {
		System.setProperty("hadoop.home.dir", "C:/hadoop");
		System.setProperty("spark.sql.warehouse.dir", "file:///C:/spark-warehouse");
		SparkSession session = SparkSession.builder().appName("Stackoverflow").master("local[*]").getOrCreate();
		
		Dataset<Row> data = session
				.read()
				.format("com.databricks.spark.csv")
				.option("header", "true")
				.option("inferSchema", "true")
				.load("src\\main\\resources\\RealEstate.csv");
		
		Dataset<Row> data1 = data.groupBy("Location")
			.avg("Price SQ Ft")
			.orderBy("avg(Price SQ Ft)");
		
		//data.printSchema();
		
		data1.col("avg(Price SQ Ft)").$greater(50);
	}

}
