package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EnvEvaluator implements Converter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvEvaluator.class);

    @Override
    public String convert(Method method, String s) {

        List<String> envList = new ArrayList<>();
        envList.add("qa");
        envList.add("uat");
        envList.add("preprod");
        envList.add("prod");


        if (envList.contains(s)) {
            return s;
        }
        else {
            LOGGER.error(String.format("The 'env' value passed in is incorrect. Value passed >> %s" +
                    "\n'env' is CASE-SENSITIVE and should always be in lower case." +
                    "\nABORTING ALL EXECUTION..." +
                    "\nThe available options are: " +
                    "\n1. qa" +
                    "\n2. uat" +
                    "\n3. preprod" +
                    "\n4. prod", s));
            System.exit(1);
            return null;
        }
    }
}
