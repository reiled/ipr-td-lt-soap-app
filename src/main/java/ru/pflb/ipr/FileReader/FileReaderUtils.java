package ru.pflb.ipr.FileReader;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.pflb.ipr.RandomNumber.RandomNumberUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

@Component
public class FileReaderUtils {

    private static final List<String> availableBaseDirs = new ArrayList<String>();

    @PostConstruct
    public void initData() {
        try (BufferedReader br = new BufferedReader(new FileReader("available_dirs.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                availableBaseDirs.add(line);
            }
        } catch (IOException e) {
            // do nothing
        }
    }

    public boolean isPathAvailable(String path) {
        Assert.notNull(path, "Path is null");
        return availableBaseDirs.stream().map(String::toLowerCase).anyMatch(path.toLowerCase()::startsWith);
    }

    public static String readRandomLineFromFile(String path) {
        Assert.notNull(path, "Path should'nt be null");
        Assert.isTrue(fileExists(path), "File doesn't exist (" + path + ")");

        String result = "";
        try (Stream<String> lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            result = lines.skip(RandomNumberUtils.generateNextInt(0, getLinesCountInFile(path))).findFirst().get();
        } catch (IOException ex) {
            Assert.isTrue(false, ex.getMessage());
        }

        return result;
    }

    private static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    private static int getLinesCountInFile(String path) {
        int linesCount = -1;

        try (Stream<String> lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            linesCount = (int)lines.count();
        } catch (IOException ex) {
            Assert.isTrue(false, ex.getMessage());
        }

        return linesCount;
    }
}
