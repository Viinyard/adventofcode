package dev.vinyard.aoc.plugins;

import dev.vinyard.bp.plugins.api.BluePrinterPlugin;
import dev.vinyard.bp.plugins.api.PluginContext;

public class AocScrapperPlugin extends BluePrinterPlugin {

    public AocScrapperPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        log.trace("AocScrapperPlugin.start()");
    }

    @Override
    public void stop() {
        log.trace("AocScrapperPlugin.stop()");
    }

    @Override
    public void delete() {
        log.trace("AocScrapperPlugin.delete()");
    }
}
