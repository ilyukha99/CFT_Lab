import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import static java.nio.file.StandardOpenOption.*;
import java.nio.charset.StandardCharsets;

public class FileWorker {

    public static List<String> readFile(Path path) {
        if (Files.isReadable(path) && !Files.isDirectory(path)) {
            try {
                return Files.lines(path).collect(Collectors.toList());
            }
            catch (IOException exc) {
                System.err.println(exc.getMessage());
            }
        }
        return null;
    }

    public static <T> void writeFile(Path path, List<T> lines) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.deleteIfExists(path);
        }
        else throw new IllegalArgumentException("Can't write to a directory file.");

        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, CREATE_NEW, WRITE)) {
            for (T line : lines) {
                writer.write(line.toString() + "\n");
            }
        }
        catch(IOException exc) { System.err.println(exc.getMessage()); }
    }

    public static boolean checkFileSort(Path path) {
        List<String> lines = readFile(path);
        if (Validator.options.contains("-i") && lines != null) {
            Integer tmp = Integer.parseInt(lines.get(0));
            int size = lines.size();
            for (int it = 1; it < size; ++it) {
                if (tmp.compareTo(Integer.parseInt(lines.get(it))) > 0) {
                    return false;
                }
                tmp = Integer.parseInt(lines.get(it));
            }
            return true;
        }

        else if (Validator.options.contains("-s") && lines != null) {
            String tmp = lines.get(0);
            int size = lines.size();
            for (int it = 1; it < size; ++it) {
                if (tmp.compareTo(lines.get(it)) > 0) {
                    return false;
                }
                tmp = lines.get(it);
            }
            return true;
        }
        return false;
    }
}
