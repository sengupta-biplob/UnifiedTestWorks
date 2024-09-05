package org.master.framework.annotations;



import org.master.framework.enums.AppNamePolicy;
import org.master.framework.enums.TestSuitePolicy;
import org.master.framework.enums.TestTypePolicy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TestDetails {

    AppNamePolicy applicationUnderTest();
    TestTypePolicy testType() default TestTypePolicy.WEB_TEST;
    String testDataId() default "";
    String xrayTestSummary();
    String xrayTestDescription() default " ";
    TestSuitePolicy[] testsuite();
    String[] steps();
    String dataProvider() default "";
    String[] labels() default "";
    String[] requirementKeys() default "";
    String author();
}
