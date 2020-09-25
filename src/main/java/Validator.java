import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Validator {
    public static String options;
    public static List<Path> inPutFilesPaths = new ArrayList<>();
    public static Path outPutFilePath;

    public static void validate(Pair<List<String>, List<String>> info) throws IllegalArgumentException {
        List<String> keys = info.getFirst();
        List<String> fileNames = info.getSecond();

        if (keys.size() == 0) {
            throw new IllegalArgumentException("Too few arguments. There must be either -s or -i.");
        }

        if (keys.contains("-s") && keys.contains("-i")) {
            throw new IllegalArgumentException("Bad options given. You can't use -s and -i together.");
        }

        if (keys.contains("-a") && keys.contains("-d")) {
            throw new IllegalArgumentException("Bad options given. You can't use -a and -d together.");
        }

        if (fileNames.size() < 2) {
            throw new IllegalArgumentException("To few arguments. Not enough files specified.");
        }

        int size = fileNames.size();
        for (int it = 1; it < size; ++it) {
            Path path = Paths.get(fileNames.get(it));
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("A non-existent input file given.");
            }
        }

        for(String key : keys) {
            options += key;
        }
        outPutFilePath = Paths.get(fileNames.get(0));
        for (int it = 1; it < fileNames.size(); ++it) {
            inPutFilesPaths.add(Paths.get(fileNames.get(it)));
        }
    }
}
