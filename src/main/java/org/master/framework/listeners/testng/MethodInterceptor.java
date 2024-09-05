package org.master.framework.listeners.testng;

import org.master.framework.annotations.TestDetails;
import org.master.framework.enums.TestSuitePolicy;
import org.master.framework.enums.TestTypePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.master.framework.config.FrameworkConfigFactory.getFrameworkConfig;

public class MethodInterceptor implements IMethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodInterceptor.class);

    private final Map<TestSuitePolicy, Predicate<IMethodInstance>> suiteFilterService = new HashMap<>();
    private final Map<TestTypePolicy, Predicate<IMethodInstance>> testTypeFilterService = new HashMap<>();

    private final Predicate<IMethodInstance> FILTER_SUT_NAME = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).applicationUnderTest().name()
            .equalsIgnoreCase(getFrameworkConfig().sut());

    /*private final Predicate<IMethodInstance> FILTER_TEST_TYPE = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).testType()
            .equals(getFrameworkConfig().testType());*/

    private final Predicate<IMethodInstance> FILTER_DEMO_SUITE = m -> Arrays.stream(m.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(TestDetails.class).testsuite())
            .collect(Collectors.toList())
            .contains(TestSuitePolicy.DEMO);

    private final Predicate<IMethodInstance> FILTER_SMOKE_SUITE = m -> Arrays.stream(m.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(TestDetails.class).testsuite())
            .collect(Collectors.toList())
            .contains(TestSuitePolicy.SMOKE);

    private final Predicate<IMethodInstance> FILTER_REGRESSION_SUITE = m -> Arrays.stream(m.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(TestDetails.class).testsuite())
            .collect(Collectors.toList())
            .contains(TestSuitePolicy.REGRESSION);

    private final Predicate<IMethodInstance> FILTER_SANITY_SUITE = m -> Arrays.stream(m.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(TestDetails.class).testsuite())
            .collect(Collectors.toList())
            .contains(TestSuitePolicy.SANITY);

    private final Predicate<IMethodInstance> FILTER_TEST_CASE_ID = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).testDataId()
            .equalsIgnoreCase(getFrameworkConfig().testId());

    private final Predicate<IMethodInstance> FILTER_WEB_TEST_TYPE = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).testType()
            .equals(TestTypePolicy.WEB_TEST);

    private final Predicate<IMethodInstance> FILTER_API_TEST_TYPE = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).testType()
            .equals(TestTypePolicy.API_TEST);

    private final Predicate<IMethodInstance> FILTER_MOBILE_TEST_TYPE = m -> m.getMethod()
            .getConstructorOrMethod()
            .getMethod()
            .getAnnotation(TestDetails.class).testType()
            .equals(TestTypePolicy.MOBILE_TEST);

    {
        suiteFilterService.put(TestSuitePolicy.DEMO, FILTER_DEMO_SUITE);
        suiteFilterService.put(TestSuitePolicy.SMOKE, FILTER_SMOKE_SUITE);
        suiteFilterService.put(TestSuitePolicy.SANITY, FILTER_SANITY_SUITE);
        suiteFilterService.put(TestSuitePolicy.REGRESSION, FILTER_REGRESSION_SUITE);

        testTypeFilterService.put(TestTypePolicy.WEB_TEST, FILTER_WEB_TEST_TYPE);
        testTypeFilterService.put(TestTypePolicy.API_TEST, FILTER_API_TEST_TYPE);
        testTypeFilterService.put(TestTypePolicy.MOBILE_TEST, FILTER_MOBILE_TEST_TYPE);
    }

    @Override
    public final List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {



        List<IMethodInstance> modifiedList = getFrameworkConfig().testId().length() > 0 ?
                list.stream()
                        .filter(FILTER_SUT_NAME
                                .and(suiteFilterService.get(getFrameworkConfig().suite()))
                                .and(FILTER_TEST_CASE_ID)
                        )
                        .collect(Collectors.toList()) :
                list.stream()
                        .filter(FILTER_SUT_NAME
                                .and(suiteFilterService.get(getFrameworkConfig().suite())))
                        .collect(Collectors.toList());



        if (modifiedList.size() == 0) {

            throw new RuntimeException("-------------------------------------" +
                    "\nNo test matching the runtime parameters are found." +
                    "\nSkipping all execution." +
                    "\n-------------------------------------");
        }

        return modifiedList;

    }


}
