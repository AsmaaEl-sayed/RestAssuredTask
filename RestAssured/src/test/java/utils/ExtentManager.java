package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentManager {

	private static ExtentReports extent;
	public static ExtentReports createInstance() {
		ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/RestAssuredReports.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		return extent;
	}

}