package tutorial;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkStackoverflow01 {
	
	public static void main(String[] args) {
		System.setProperty("hadoop.home.dir", "C:/hadoop");
		System.setProperty("spark.sql.warehouse.dir", "file:///C:/spark-warehouse");
		SparkSession session = SparkSession.builder().appName("Stackoverflow").master("local[*]").getOrCreate();
		
		Dataset<Row> data = session
				.read()
				.format("com.databricks.spark.csv")
				.option("header", "true")
				.option("inferSchema", "true")
				.load("src\\main\\resources\\2016-stack-overflow-survey-responses.csv");
		
		
		System.out.println("*** It only prints four columns ***");
		Dataset<Row> dataFiltered = data.
				select("country","occupation","age_midpoint","salary_midpoint");
		
		System.out.println("*** It prints data about Afghanistan and column age_midpoint is not null ***");
		dataFiltered.filter(dataFiltered.col("country").equalTo("Afghanistan"))
					.filter(dataFiltered.col("age_midpoint").notEqual(null))
					.show();
		
		System.out.println("*** Data grouped by occupation column ***");
		dataFiltered.groupBy("occupation").count().show();
		
		
		dataFiltered.filter(dataFiltered.col("age_midpoint").$less(20)).show();
		
		
		System.out.println("*** Data ordered by salary_midpoint column ***");
		dataFiltered.orderBy(dataFiltered.col("salary_midpoint").desc()).show();
		
		
		System.out.println("*** Data ordered by salary_midpoint column ***");
		dataFiltered.orderBy(dataFiltered.col("salary_midpoint").asc()).show();
		
		
		dataFiltered.groupBy("country").avg("salary_midpoint").show();
		
		dataFiltered.groupBy("country")
					.avg("salary_midpoint")
					.orderBy("avg(salary_midpoint)")
					.show();
		
		session.stop();
		//data.printSchema();
		
	}
}
