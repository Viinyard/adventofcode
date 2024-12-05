package dev.vinyard.aoc.plugins.extensions;

import dev.vinyard.blueprinter.plugins.api.BluePrinterExtension;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.pf4j.Extension;

@Extension
@Slf4j
public class VelocityEnvironmentExtension implements BluePrinterExtension {

    @Override
    public void init(VelocityContext velocityContext) {
        log.info("Adding aocEnvironment to velocity context.");
        velocityContext.put("aocEnvironment", this);
    }

    public static String getProperty(String key) {
        String property = System.getenv(key);
        log.info("getProperty: {} = {}", key, property);
        return property;
    }
}
