package org.master.framework.listeners.testng;


import org.master.framework.config.FrameworkConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

/**
 * TestListener class implements the TestNG ITestListener and ISuiteListener interfaces.
 * It is used to listen to events related to the TestNG test cases and test suites and
 * perform actions based on those events.
 * This class handles events such as test start, test success, test failure, test skipped,
 * and test finish.
 * It initialises the extent report and test details to the report.
 * This class provides methods to format and log test-related information and exceptions.
 *
 * @author Biplob Sengupta
 * @since 1.0.0
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    public final void onStart(ITestContext context) {


        String env = FrameworkConfigFactory.getFrameworkConfig().env();
        String browser = FrameworkConfigFactory.getFrameworkConfig().browser().name();
        String suite = FrameworkConfigFactory.getFrameworkConfig().suite().name();
        boolean headless = FrameworkConfigFactory.getFrameworkConfig().headless();
        LOGGER.info("+------------------------------------------------------------------+");
        LOGGER.info("STARTING TEST SUITE WITH THE FOLLOWING CONFIGURATIONS");
        LOGGER.info("ENV: " + env);
        if (headless)
            LOGGER.info("HEADLESS BROWSER: " + browser);
        else
            LOGGER.info("BROWSER: " + browser);
        LOGGER.info("SUITE: " + suite);


        ExtentReport.initExtentReport();

    }

    @SuppressWarnings("unchecked")
    public final void onTestStart(ITestResult testResult) {

        String[][] testSteps;

        String testCaseId = Optional.ofNullable(testResult.getMethod()
                        .getConstructorOrMethod().getMethod()
                        .getAnnotation(TestCaseId.class).value())
                .orElse(testResult.getMethod().getConstructorOrMethod().getMethod().getName());

        String testName = Optional.ofNullable(testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).xrayTestSummary()).orElse("");

        String author = Optional.ofNullable(testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).author()).orElse("");

        String[] steps = testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).steps();

        String completeTestName = (testCaseId.isBlank() || testCaseId.isEmpty()) ? testName : testCaseId + ": " + testName;

        testSteps = new String[steps.length + 1][2];
        testSteps[0][0] = "STEP NO.";
        testSteps[0][1] = "STEP ACTION";
        int stepIdx = 1;
        for (String step : steps) {
            String[] temp = step.split(":");
            if (temp.length == 2) {
                testSteps[stepIdx][0] = temp[0];
                testSteps[stepIdx][1] = temp[1];
            } else {
                testSteps[stepIdx][0] = "Step " + String.valueOf(stepIdx);
                testSteps[stepIdx][1] = temp[0];
            }
            stepIdx++;
        }


        LOGGER.info("Starting test: " + completeTestName);
        try {
            Map<String, String> data = (Map<String, String>) (testResult.getParameters())[0];
            String[][] test_data = new String[data.size() + 1][2];
            test_data[0][0] = "TEST DATA KEY";
            test_data[0][1] = "TEST DATA VALUE";
            int index = 1;
            for (Map.Entry<String, String> entry : data.entrySet()) {

                test_data[index][0] = entry.getKey();
                test_data[index][1] = entry.getValue();

                index++;
            }

            ExtentReport.createTest(completeTestName, testSteps, test_data);
        }
        catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            ExtentReport.createTest(completeTestName, testSteps);
        }

        ExtentReport.getExtentTest().assignAuthor(author);
        ExtentReport.getExtentTest().assignCategory(FrameworkConfigFactory.getFrameworkConfig().language());


    }

    @SuppressWarnings("unchecked")
    public final void onTestSuccess(ITestResult testResult) {

        String testCaseId = Optional.ofNullable(testResult.getMethod()
                        .getConstructorOrMethod().getMethod()
                        .getAnnotation(TestCaseId.class).value())
                .orElse(testResult.getMethod().getConstructorOrMethod().getMethod().getName());

        String testName = Optional.ofNullable(testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).xrayTestSummary()).orElse("");

        String completeTestName = (testCaseId.isBlank() || testCaseId.isEmpty()) ? testName : testCaseId + ": " + testName;

        LOGGER.info(completeTestName + " - PASSED");

    }


    public final void onTestFailure(ITestResult testResult) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        testResult.getThrowable().printStackTrace(pw);
        String failMsg = sw.toString();


        String testCaseId = Optional.ofNullable(testResult.getMethod()
                        .getConstructorOrMethod().getMethod()
                        .getAnnotation(TestCaseId.class).value())
                .orElse(testResult.getMethod().getConstructorOrMethod().getMethod().getName());

        String testName = Optional.ofNullable(testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).xrayTestSummary()).orElse("");

        String completeTestName = (testCaseId.isBlank() || testCaseId.isEmpty()) ? testName : testCaseId + ": " + testName;

        LOGGER.error(completeTestName + " - FAILED");
        LOGGER.error(completeTestName + " - Failure Stacktrace: " + failMsg);


        ReportLogger.fail(failMsg);

    }

    public final void onTestSkipped(ITestResult testResult) {

        String testCaseId = Optional.ofNullable(testResult.getMethod()
                        .getConstructorOrMethod().getMethod()
                        .getAnnotation(TestCaseId.class).value())
                .orElse(testResult.getMethod().getConstructorOrMethod().getMethod().getName());

        String testName = Optional.ofNullable(testResult.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TestDetails.class).xrayTestSummary()).orElse("");

        String completeTestName = (testCaseId.isBlank() || testCaseId.isEmpty()) ? testName : testCaseId + ": " + testName;

        LOGGER.warn(completeTestName + " - SKIPPED.");
        LOGGER.warn(completeTestName + " - Re-trying as this failed due to " + testResult.getThrowable().toString());

        ReportLogger.skip("Skipping Test Result and Re-Trying. Failed because: " + testResult.getThrowable().toString());
    }

    public final void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public final void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }


    public final void onFinish(ITestContext context) {

        LOGGER.info("onFinish");
        ExtentReport.flushReport();
        LOGGER.info("After Extent Report Flush");
        LOGGER.info("onFinish end");

    }
}

