package org.master.framework.listeners.testng;

import org.master.framework.annotations.TestDetails;
import org.master.framework.enums.TestSuitePolicy;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation testAnnotation, Class className, Constructor testConstructor, Method testMethod) {

        TestSuitePolicy[] testSuite = testMethod.getAnnotation(TestDetails.class).testsuite();
        String dataProvider = testMethod.getAnnotation(TestDetails.class).dataProvider();

        String[] groups = new String[testSuite.length];

        for (int i = 0; i < testSuite.length; i++)
            groups[i] = testSuite[i].name();

        testAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
        testAnnotation.setGroups(groups);
        testAnnotation.setDataProvider(dataProvider);

        /*testAnnotation.setTestName(testMethod.getAnnotation(WebTest.class).name());

        testAnnotation.setPriority(testMethod.getAnnotation(WebTest.class).priority());

        testAnnotation.setDataProvider(testMethod.getAnnotation(WebTest.class).dataProvider());

        testAnnotation.setDataProviderClass(testMethod.getAnnotation(WebTest.class).dataProviderClass());*/
    }
}
