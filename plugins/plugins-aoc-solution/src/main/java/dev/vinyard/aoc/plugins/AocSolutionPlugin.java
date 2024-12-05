package dev.vinyard.aoc.plugins;

import dev.vinyard.blueprinter.plugins.api.BluePrinterPlugin;
import dev.vinyard.blueprinter.plugins.api.PluginContext;

public class AocSolutionPlugin extends BluePrinterPlugin {

    public AocSolutionPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        log.trace("AocSolutionPlugin.start()");
    }

    @Override
    public void stop() {
        log.trace("AocSolutionPlugin.stop()");
    }

    @Override
    public void delete() {
        log.trace("AocSolutionPlugin.delete()");
    }
}
