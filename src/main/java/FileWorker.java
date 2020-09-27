import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import static java.nio.file.StandardOpenOption.*;
import java.nio.charset.StandardCharsets;

public class FileWorker {

    public static MyArrayList<String> readFile(Path path) {
        if (Files.isReadable(path) && !Files.isDirectory(path)) {
            try {
                return Files.lines(path).collect(Collectors.toCollection(MyArrayList::new));
            }
            catch (IOException exc) {
                System.err.println(exc.getMessage());
            }
        }
        return null;
    }

    public static <T> void writeFile(Path path, List<T> lines) throws IOException {

        BufferedWriter writer = getWriter(path);
        if (writer != null) {
            for (T line : lines) {
                writer.write(line.toString() + "\n");
            }
            writer.flush();
            writer.close();
        }
    }

    public static boolean checkFileSort(Path path) throws IOException {
        if ((Files.isReadable(path) && !Files.isDirectory(path)))
        try(Scanner scanner = new Scanner(path).useDelimiter("\\n")) {
            if (scanner.hasNextInt() && Validator.options.contains("-i")) {
                int val = scanner.nextInt(), nextVal;
                while(scanner.hasNext()) {
                    nextVal = scanner.nextInt();
                    if ((val - nextVal) * Sorter.flag > 0) {
                        return false;
                    }
                    val = nextVal;
                }
                return true;
            }
            if (scanner.hasNext() && Validator.options.contains(("-s"))) {
                String str = scanner.next(), nextStr;
                while (scanner.hasNext()) {
                    nextStr = scanner.next();
                    if (str.compareTo(nextStr) * Sorter.flag > 0) {
                        return false;
                    }
                    str = nextStr;
                }
                return true;
            }
        }
        return false;
    }

    public static BufferedWriter getWriter(Path path) {

        try {
            if (!Files.isDirectory(Validator.outPutFilePath)) {
                Files.deleteIfExists(Validator.outPutFilePath);
            } else throw new IllegalArgumentException("Can't write to a directory file.");

             return Files.newBufferedWriter(path, StandardCharsets.UTF_8, CREATE_NEW, WRITE);
        }
        catch (IOException exc) {
            System.err.println(exc.getMessage());
        }
        return null;
    }
}
