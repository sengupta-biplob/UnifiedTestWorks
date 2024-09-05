package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class ProjectNameEvaluator implements Converter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectNameEvaluator.class);
    @Override
    public String convert(Method method, String projectName) {

        List<String> projectList = List.of("africa",
                "brazil",
                "hytec",
                "latam");
        if (projectList.contains(projectName)) {
            return projectName;
        } else {
            LOGGER.error(String.format("The 'sut' value passed in is incorrect. Value passed >> %s" +
                    "\n'sut' is CASE-SENSITIVE and should always be in lower case." +
                    "\nABORTING ALL EXECUTION..." +
                    "\nThe available options are: " +
                    "\n1. africa" +
                    "\n2. brazil" +
                    "\n3. hytec" +
                    "\4. latam", projectName));
            System.exit(1);
            return null;
        }
    }
}
