package dev.vinyard.adventofcode.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileReader {
    public static String readFileToString(String fileName) throws IOException, URISyntaxException {
        URL url = Optional.ofNullable(FileReader.class.getClassLoader().getResource(fileName)).orElseThrow(() -> new FileNotFoundException("File not found"));
        Path filePath = Path.of(url.toURI());

        return Files.readString(filePath);
    }
}
