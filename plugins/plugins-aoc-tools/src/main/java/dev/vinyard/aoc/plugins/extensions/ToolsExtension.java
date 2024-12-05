package dev.vinyard.aoc.plugins.extensions;

import dev.vinyard.blueprinter.plugins.api.BluePrinterExtension;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.pf4j.Extension;

@Extension
@Slf4j
public class ToolsExtension implements BluePrinterExtension {
    @Override
    public void init(VelocityContext velocityContext) {
        log.info("Adding date to velocity context.");
        velocityContext.put("date", new DateTool());

        log.info("Adding math to velocity context.");
        velocityContext.put("math", new MathTool());
    }
}
