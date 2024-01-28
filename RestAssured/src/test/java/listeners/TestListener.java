package listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import utils.ExtentManager;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass(MarkupHelper.createLabel("Test Case Passed", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }


@AfterSuite
public  void teardown() throws IOException {
	extent.flush();
	Desktop.getDesktop().browse(new File("reportWithScreenshoot.html").toURI());	
}

    public static ExtentTest getTest() {
        return test.get();
    }
}