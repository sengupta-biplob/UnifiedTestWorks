package org.master.framework.config.evaluators;

import org.aeonbits.owner.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class LanguageEvaluator implements Converter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageEvaluator.class);

    @Override
    public String convert(Method method, String language) {

        List<String> languageList = List.of("english",
                "mandarin",
                "hindi",
                "spanish",
                "french",
                "arabic",
                "bengali",
                "russian",
                "portuguese",
                "urdu",
                "indonesian",
                "german",
                "japanese",
                "chinese");



        if (languageList.contains(language)) {
            return language;
        }
        else {
            LOGGER.error(String.format("The 'language' value is either incorrect or not passed in. Value passed >> '%s'." +
                    "\nThis value is MANDATORY." +
                    "\nEither pass in from the run.config.properties file or as a cmd line parameter." +
                    "\n'language' is CASE-SENSITIVE and should always be in lower case." +
                    "\nABORTING ALL EXECUTION..." +
                    "\nThe available options are: " +
                    "\n1. english" +
                    "\n2. spanish" +
                    "\n3. french" +
                    "\n4. german" +
                    "\n5. portuguese" +
                    "\n6. russian" +
                    "\n7. arabic" +
                    "\n8. bengali" +
                    "\n9. hindi" +
                    "\n10. urdu" +
                    "\n11. mandarin" +
                    "\n12. chinese" +
                    "\n13. japanese" +
                    "\n14. indonesian" +
                    "\n15. turkish", language));
            System.exit(1);
            return null;
        }
    }
}