package net.coma112.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@UtilityClass
public class FileUtils {
    private static final String SUFFIX = ".txt";

    public Path getTextFile(@NonNull String fileName) {
        String resourcePath = fileName + SUFFIX;
        InputStream resourceStream = FileUtils.class.getClassLoader().getResourceAsStream(resourcePath);

        if (resourceStream == null) throw new IllegalArgumentException("Nincsen ilyen fájl");

        try {
            Path tempFile = Files.createTempFile("resource", SUFFIX);
            Files.copy(resourceStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } catch (Exception ignored) {
            throw new RuntimeException("Nem sikerült betölteni a fájlt!");
        }
    }
}
