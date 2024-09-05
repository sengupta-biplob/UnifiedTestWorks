package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.kohler.webdriver.core.enums.TestSuitePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuiteEvaluator implements Converter<TestSuitePolicy> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageEvaluator.class);

    @Override
    public TestSuitePolicy convert(Method method, String suite) {

        List<String> suiteList = new ArrayList<>();
        suiteList.add("smoke");
        suiteList.add("sanity");
        suiteList.add("regression");
        suiteList.add("demo");

        if (!suiteList.contains(suite)) {
            LOGGER.error(String.format("The 'env' value passed in is incorrect. Value passed >> %s" +
                    "\n'env' is CASE-SENSITIVE and should always be in lower case." +
                    "\nABORTING ALL EXECUTION..." +
                    "\nThe available options are: " +
                    "\n1. smoke" +
                    "\n2. regression" +
                    "\n3. sanity" +
                    "\n4. demo", suite));
            System.exit(1);
            return null;
        }

        Map<String, TestSuitePolicy> suiteType = new HashMap<>();
        suiteType.put("SMOKE", TestSuitePolicy.SMOKE);
        suiteType.put("SANITY", TestSuitePolicy.SANITY);
        suiteType.put("REGRESSION", TestSuitePolicy.REGRESSION);
        suiteType.put("DEMO", TestSuitePolicy.DEMO);

        return suiteType.getOrDefault(suite.toUpperCase(), TestSuitePolicy.SMOKE);

    }
}
