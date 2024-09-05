package org.master.framework.listeners.testng;

import org.master.framework.config.FrameworkConfigFactory;
import org.openqa.selenium.SessionNotCreatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryAnalyzer.class);
    private static final ThreadLocal<Integer> retryCount = ThreadLocal.withInitial(() -> 0);
    private static final int MAX_RETRY_COUNT = FrameworkConfigFactory.getFrameworkConfig().retry();

    public synchronized boolean retry(ITestResult result) {

        if (!result.isSuccess() || result.getThrowable() instanceof SessionNotCreatedException) {
            if (retryCount.get() < MAX_RETRY_COUNT) {
                if (result.getParameters().length > 0) {
                    result.setStatus(ITestResult.FAILURE);
                }
                retryCount.set(retryCount.get() + 1);
                return true;
            }
            else {
                result.setStatus(ITestResult.FAILURE);
                retryCount.set(0);
                return false;
            }
        }
        retryCount.set(0);
        result.setStatus(ITestResult.SUCCESS);
        return false;
    }

}
