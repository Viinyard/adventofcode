package dev.vinyard.aoc.plugins;

import dev.vinyard.blueprinter.plugins.api.BluePrinterPlugin;
import dev.vinyard.blueprinter.plugins.api.PluginContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AocEnvironmentPlugin extends BluePrinterPlugin {

    public AocEnvironmentPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        log.trace("dev.vinyard.aoc.plugins.AocEnvironmentPlugin.start()");
    }

    @Override
    public void stop() {
        log.trace("dev.vinyard.aoc.plugins.AocEnvironmentPlugin.stop()");
    }

    @Override
    public void delete() {
        log.trace("dev.vinyard.aoc.plugins.AocEnvironmentPlugin.delete()");
    }
}
