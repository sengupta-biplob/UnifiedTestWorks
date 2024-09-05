package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;

public class PushExecutionEvaluator implements Converter<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectNameEvaluator.class);

    @Override
    public Boolean convert(Method method, String s) {
       if (Objects.nonNull(s))
           return s.equalsIgnoreCase("true");
       else
           return false;
    }
}
