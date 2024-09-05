package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;

public class HeadlessBrowserEvaluator implements Converter<Boolean> {

    @Override
    public Boolean convert(Method method, String headless) {
        return headless.equalsIgnoreCase("true");
    }
}
