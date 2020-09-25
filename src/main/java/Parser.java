import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private static final String[] allOptions = { "-s", "-i", "-a", "-d" };

    public static Pair<List<String>, List<String>> parseArgs(String[] args) {
        List<String> optionList = Arrays.asList(allOptions);
        optionList = Stream.of(args).filter(optionList::contains).distinct().collect(Collectors.<String>toList());

        List<String> fileNames = Stream.of(args).filter(str -> str.contains("."))
                .distinct().collect(Collectors.<String>toList());

        return new Pair<>(optionList, fileNames);
    }
}
