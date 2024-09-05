package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;

public class URLEvaluator implements Converter<String> {
    @Override
    public String convert(Method method, String url) {
        return url.toLowerCase();
    }
}
