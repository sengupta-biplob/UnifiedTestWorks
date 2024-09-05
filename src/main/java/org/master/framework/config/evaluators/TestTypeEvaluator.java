package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.kohler.webdriver.core.enums.TestTypePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestTypeEvaluator implements Converter<TestTypePolicy> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTypeEvaluator.class);

    @Override
    public TestTypePolicy convert(Method method, String testType) {

        Map<String, TestTypePolicy> testTypeMap = new HashMap<>();
                testTypeMap.put("WEB_TEST", TestTypePolicy.WEB_TEST);
                testTypeMap.put("API_TEST", TestTypePolicy.API_TEST);
                testTypeMap.put("MOBILE_TEST", TestTypePolicy.MOBILE_TEST);


        //System.out.println("TestType:" + testType +":");

        /*if (Objects.nonNull(testType) || !testType.isEmpty())
            return testTypeMap.getOrDefault(testType.toUpperCase(), TestTypePolicy.WEB_TEST);
        else
            return testTypeMap.get("WEB_TEST");*/
        /*try {
            return testTypeMap.getOrDefault(testType.toUpperCase(), TestTypePolicy.WEB_TEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return testTypeMap.get("WEB_TEST");
        }*/

        if (Objects.nonNull(testType)) {

            try {
                TestTypePolicy testTypePolicy = testTypeMap.get(testType.toUpperCase());
                return testTypePolicy;
            }
            catch (Exception e) {
                e.printStackTrace();
                return testTypeMap.get("WEB_TEST");
            }

        }
        else
            return testTypeMap.get("WEB_TEST");


    }
}
