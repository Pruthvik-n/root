package com.root;

import com.root.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.root.Util.getLinesFromFile;

public class Tracker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);

    private static final Map<String, TripSummary> tripSummaryMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Error! Input file Required. Please enter the path of the file.");
            System.out.println("Example Usage : ./gradlew run --args=path-to-my-file-name");
            System.exit(1);
        }

        String filePath = args[0];
        Stream<String> lines = getLinesFromFile(filePath);
        lines.map(Util::extract)
                .filter(Objects::nonNull)
                .filter(Command::isExecutable)
                .forEach(cmd -> cmd.execute(tripSummaryMap));

        Stream<Map.Entry<String, TripSummary>> sortedTripSummary =
                tripSummaryMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue());
        
        generateReport(sortedTripSummary);
        
    }

    private static void generateReport(Stream<Map.Entry<String, TripSummary>> tripSummary) throws IOException {
        Files.deleteIfExists(Path.of("report.txt"));
        Path report = Files.createFile(Path.of("report.txt"));

        tripSummary.forEach(s -> {
            try {
                Files.writeString(report, s.getValue().toString(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                LOGGER.error("There was an error writing to file.");
            }
        });

        LOGGER.info("Report generated at {}", report.toAbsolutePath().toString());
    }

}
