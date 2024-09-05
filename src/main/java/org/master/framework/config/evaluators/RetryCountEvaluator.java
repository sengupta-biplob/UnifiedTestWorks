package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;

import java.lang.reflect.Method;
import java.util.Objects;

public class RetryCountEvaluator implements Converter<Integer> {

    @Override
    public Integer convert(Method method, String retry) {
        return Objects.nonNull(retry)? Integer.parseInt(retry): 0;
    }
}
