package dev.vinyard.aoc.blueprinter;

import dev.vinyard.bp.autoconfigure.EnvironmentProperties;
import dev.vinyard.bp.core.generation.GenerationManager;
import dev.vinyard.bp.core.prompt.Prompt;
import dev.vinyard.bp.core.prompt.PromptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties({EnvironmentProperties.class})
@Slf4j
public class BluePrinterGenRunner {

    public static void main(String[] args) {
        SpringApplication.run(BluePrinterGenRunner.class, args);
    }

    @Bean
    public ApplicationRunner bluePrinterApplicationRunner(GenerationManager generationManager, PromptRepository promptRepository) {
        return args -> {
            args.getOptionNames().stream()
                    .map(key -> new Prompt(key, args.getOptionValues(key)))
                    .forEach(promptRepository::save);

            promptRepository.findAll().forEach(prompt -> log.info("Prompt: {}", prompt));

            generationManager.generate(args.getNonOptionArgs().getFirst());

            promptRepository.deleteAll();
        };
    }
}
