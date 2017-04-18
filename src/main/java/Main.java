import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String SOURCE_CODE_ROOT = "C:\\projects\\roots\\ifrs9";
    private static final String WORKING_DIRECTORY = "D:\\tmp\\codemaat";
    private static final boolean WEIGH_NUMBER_OF_CHANGES = false;

    public static void main(String[] args) throws IOException {

        List<FileHotspotIndicators> changes = getChangeCountPerFile();
        changes.sort((c1, c2) -> {
            int changeCompare = c2.getChangeCount().compareTo(c1.getChangeCount());
            if (changeCompare != 0) {
                return changeCompare;
            }
            return c2.getAmountOfLines().compareTo(c1.getAmountOfLines());
        });

        String filter = "(web|core).*";

        PrintWriter writer = new PrintWriter(WORKING_DIRECTORY + File.separator + "out.csv");
        changes.stream()
                .filter(c -> c.getAmountOfLines() > 0 && c.getChangeCount() > 10)
                .filter(c -> c.getFileName().matches(filter))
                .forEach(c -> writer.write(c.getFileName() + ";" + c.getChangeCount() + ";" + c.getAmountOfLines() + "\n"));
        writer.close();
    }

    private static List<FileHotspotIndicators> getChangeCountPerFile() throws IOException {

        Map<String, Integer> countPerFile = new HashMap<>();
        Pattern linePattern = Pattern.compile("(\\d*)\t(\\d*)\t(.*)");

        Files.lines(Paths.get(WORKING_DIRECTORY + File.separator + "cm_input.csv")).forEach(line -> {
            if (line.matches("\\d.*")) {

                Matcher matcher = linePattern.matcher(line);
                if (matcher.matches()) {
                    String fileName = matcher.group(3);

                    Integer numberOfChanges;
                    if (WEIGH_NUMBER_OF_CHANGES) {
                        numberOfChanges = Integer.valueOf(matcher.group(1)) + Integer.valueOf(matcher.group(2));
                    } else {
                        numberOfChanges = 1;
                    }

                    countPerFile.putIfAbsent(fileName, 0);
                    countPerFile.put(fileName, countPerFile.get(fileName) + numberOfChanges);
                }
            }
        });

        List<FileHotspotIndicators> changes = new ArrayList<>();
        countPerFile.forEach((file, count) -> {
            Path path = Paths.get(SOURCE_CODE_ROOT + File.separator + file);
            int amountOfLines;
            if (path.toFile().exists()) {
                try {
                    amountOfLines = Files.readAllLines(path).size();
                } catch (IOException e) {
                    amountOfLines = 0;
                }
            } else {
                amountOfLines = 0;
            }

            changes.add(new FileHotspotIndicators(file, count, amountOfLines));


        });
        return changes;
    }
}
