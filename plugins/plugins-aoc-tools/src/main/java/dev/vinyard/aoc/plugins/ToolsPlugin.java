package dev.vinyard.aoc.plugins;

import dev.vinyard.blueprinter.plugins.api.BluePrinterPlugin;
import dev.vinyard.blueprinter.plugins.api.PluginContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToolsPlugin extends BluePrinterPlugin {

    public ToolsPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        log.trace("dev.vinyard.aoc.plugins.ToolsPlugin.start()");
    }

    @Override
    public void stop() {
        log.trace("pro.vinyard.aoc.plugins.ToolsPlugin.stop()");
    }

    @Override
    public void delete() {
        log.trace("pro.vinyard.aoc.plugins.ToolsPlugin.delete()");
    }
}
