package org.master.framework.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;

/**
 * The FrameworkConfigFactory class provides static methods for creating instances of configuration interfaces
 * using the ConfigCache and ConfigFactory from the OWNER library. This class cannot be instantiated or extended.
 * <p>
 * This class offers two methods to retrieve configuration instances:
 * <ul>
 *   <li>getFrameworkConfig() - Retrieves the default FrameworkConfig instance.</li>
 *   <li>getFrameworkConfig(Class) - Retrieves an instance of the specified configuration class.</li>
 * </ul>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * FrameworkConfig config = FrameworkConfigFactory.getFrameworkConfig();
 * }
 * </pre>
 * <pre>
 * {@code
 * FrameworkConfig config = FrameworkConfigFactory.getFrameworkConfig(Class<T>);
 * }
 * </pre>
 * <p>
 * This class ensures that configuration properties are loaded efficiently and can be accessed throughout the application.
 * </p>
 *
 * @author Biplob Sengupta
 * @see ConfigCache
 * @see FrameworkConfig
 * @see ConfigFactory
 * @since 1.0.0
 */
public final class FrameworkConfigFactory {

    private FrameworkConfigFactory() {
    }

    /**
     * Retrieves an instance of the FrameworkConfig configuration.
     * This method uses the ConfigCache to get or create the configuration instance, ensuring that the same instance
     * is reused throughout the application.
     * <p>
     * This method is particularly useful for accessing the default configuration settings defined in the FrameworkConfig interface.
     * </p>
     *
     * @return an instance of FrameworkConfig with the loaded configuration properties.
     */
    public static FrameworkConfig getFrameworkConfig() {
        return ConfigCache.getOrCreate(FrameworkConfig.class);
    }

    /**
     * Generic method to load a configuration instance of the specified class.
     * This method uses the ConfigFactory to create a new instance of the specified configuration interface.
     * <p>
     * This method allows for flexibility in loading different configuration interfaces as needed, beyond the default FrameworkConfig.
     * </p>
     *
     * @param <T> the type of the configuration interface extending Config.
     * @param configClass the class of the configuration interface to be loaded.
     * @return an instance of the specified configuration class with the loaded configuration properties.
     */
    public static <T extends Config> T getFrameworkConfig(Class<T> configClass) {
        return ConfigFactory.create(configClass);
    }
}

