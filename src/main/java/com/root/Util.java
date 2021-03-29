package com.root;

import com.root.command.Command;
import com.root.command.Driver;
import com.root.command.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    public static Command extract(String s) {
        String[] parts = s.split(" ", 2);
        if (parts.length < 2){
            LOGGER.error("Invalid command type. At least one space separated argument must exists for command.");
            System.exit(1);
        }
        return createCommand(parts[0], parts[1]);
    }

    public static Command createCommand(String cmd, String args) {
        switch (cmd){
            case "Driver":
                return new Driver(args);
            case "Trip":
                return new Trip(args);
            default:
                return null;
        }
    }

    public static Stream<String> getLinesFromFile(String filePath) {
        try {
            return Files.lines(Path.of(filePath));
        } catch (IOException e) {
            LOGGER.error("Error reading from given file path. Reason {}", e.getMessage());
        }
        return Stream.empty();
    }
}
