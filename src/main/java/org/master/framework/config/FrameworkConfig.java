package org.master.framework.config;

import org.aeonbits.owner.Config;
import org.master.framework.config.evaluators.*;
import org.master.framework.enums.BrowserTypePolicy;
import org.master.framework.enums.TestSuitePolicy;

/**
 * The FrameworkConfig interface is used to specify the configuration properties for the automation framework.
 * It extends the Config interface provided by the Aeonbits OWNER library and adds custom configuration keys.
 * The configuration properties can be loaded from multiple sources in the order of preference - system properties,
 * environment variables, and properties files located in the classpath under the configuration directory.
 *
 * The sources are specified as follows:
 * <ul>
 *   <li>system:properties</li>
 *   <li>system:env</li>
 *   <li>classpath:configuration/run.config.properties</li>
 *   <li>classpath:configuration/sut.config.properties</li>
 *   <li>classpath:configuration/xray.credentials.properties</li>
 * </ul>
 *
 * @author Biplob Sengupta
 * @since 1.0.0
 */

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:configuration/run.config.properties",
        "classpath:configuration/sut.config.properties",
        "classpath:configuration/xray.credentials.properties"
})
public interface FrameworkConfig extends Config {

    @ConverterClass(EnvEvaluator.class)
    @Key("env")
    String env();

    @Key("language")
    String language();

    @ConverterClass(BrowserEvaluator.class)
    @Key("browser")
    BrowserTypePolicy browser();

    @ConverterClass(HeadlessBrowserEvaluator.class)
    @Key("headless")
    Boolean headless();

    @ConverterClass(SuiteEvaluator.class)
    @Key("suite")
    TestSuitePolicy suite();

    @ConverterClass(RetryCountEvaluator.class)
    @Key("retry")
    Integer retry();

    @Key("test_data_id")
    String testId();

    @ConverterClass(PushExecutionEvaluator.class)
    @Key("push_execution")
    boolean pushExecution();

    @Key("${sut}.${suite}.test.plan.id")
    String testPlanKey();

    @Key("sut")
    @ConverterClass(ProjectNameEvaluator.class)
    String sut();

    @Key("${sut}.jira.key")
    String jiraProjectKey();

    @Key("${sut}.${env}.username")
    String username();

    @Key("${sut}.${env}.password")
    String password();


    @ConverterClass(URLEvaluator.class)
    @Key("${sut}.${env}.${language}.url")
    String url();

    @Key("xray.client.id")
    String xrayClientId();

    @Key("xray.client.secret")
    String xrayClientSecret();

    @Key("jira.user.email")
    String jiraUserEmail();

    @Key("jira.user.api.token")
    String jiraUserApiToken();

    @Key("report.portal.base.url")
    String rpBaseUrl();

    @Key("report.portal.uuid")
    String rpUuid();

    @Key("report.portal.project.name")
    String rpProjectName();
}
