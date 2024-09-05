package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.kohler.webdriver.core.enums.BrowserTypePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class BrowserEvaluator implements Converter<BrowserTypePolicy> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserEvaluator.class);

    @Override
    public BrowserTypePolicy convert(Method method, String browser) {

        Map<String, BrowserTypePolicy> browserMap = new HashMap<>();

        browserMap.put("CHROME", BrowserTypePolicy.CHROME);
        browserMap.put("FIREFOX", BrowserTypePolicy.FIREFOX);
        browserMap.put("EDGE", BrowserTypePolicy.EDGE);

        if (Objects.nonNull(browserMap.get(browser.toUpperCase())))
            return browserMap.get(browser.toUpperCase());

        LOGGER.warn("Browser passed in did not match with any defined values." +
                    "\nThe options are:" +
                    "\n1. CHROME" +
                    "\n2. FIREFOX" +
                    "\nDefaulting to CHROME Browser." +
                    "\nDo you wish to continue (y/n)?");

        Scanner scn = new Scanner(System.in);
        String feedback = scn.nextLine();

        if (!feedback.equalsIgnoreCase("y")) {
            LOGGER.error("USER ERROR: Incorrect Browser provided.\nABORTING ALL EXECUTION...");
            System.exit(1);
        }

        return browserMap.get("CHROME");




    }
}
