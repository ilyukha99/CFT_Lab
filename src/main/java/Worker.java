import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Worker {

    public static void startRoutine() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Termination.")));
        if (Validator.options.contains("-d")) {
            Sorter.flag = -1;
        }

        ArrayList<Path> sortedFiles = new ArrayList<>();
        ArrayList<Path> unsortedFiles = new ArrayList<>();
        for (Path path : Validator.inPutFilesPaths) {
            if (FileWorker.checkFileSort(path)) {
                sortedFiles.add(path);
            } else unsortedFiles.add(path);
        }

        if (Validator.options.contains("-s")) {
            Worker.stringRoutine(sortedFiles, unsortedFiles);
        }
        else Worker.intRoutine(sortedFiles, unsortedFiles);
    }

    public static void stringRoutine(ArrayList<Path> sortedFiles, ArrayList<Path> unsortedFiles) throws IOException {
        ArrayList<MyArrayList<String>> dataList = new ArrayList<>();
        for (Path path : sortedFiles) {
            dataList.add(FileWorker.readFile(path));
        }

        for (Path path : unsortedFiles) {
            MyArrayList<String> lines = FileWorker.readFile(path);
            Sorter.sortStrings(lines);
            dataList.add(lines);
        }

        Optional<Integer> optSize = dataList.stream().map(ArrayList::size).reduce(Integer::sum);
        if (optSize.isEmpty()) {
            System.out.println("The specified files are empty.");
            return;
        }

        MyArrayList<String> first = new MyArrayList<>(), second = new MyArrayList<>();
        endRoutine(first, second, dataList, Sorter::merge);
    }

    public static void intRoutine(ArrayList<Path> sortedFiles, ArrayList<Path> unsortedFiles) throws IOException{
        ArrayList<MyArrayList<Integer>> dataList = new ArrayList<>();
        for (Path path : sortedFiles) {
            dataList.add(FileWorker.readFile(path).stream().map(Integer::parseInt)
                    .collect(Collectors.toCollection(MyArrayList::new)));
        }

        for (Path path : unsortedFiles) {
            MyArrayList<Integer> lines = FileWorker.readFile(path).stream().map(
                    Integer::parseInt).collect(Collectors.toCollection(MyArrayList::new));
            Sorter.sortInts(lines);
            dataList.add(lines);
        }

        Optional<Integer> optSize = dataList.stream().map(ArrayList::size).reduce(Integer::sum);
        if (optSize.isEmpty()) {
            System.out.println("The specified files are empty.");
            return;
        }

        MyArrayList<Integer> first = new MyArrayList<>(), second = new MyArrayList<>();
        endRoutine(first, second, dataList, Sorter::merge);
    }

    public static <T extends Comparable<T>> void endRoutine(MyArrayList<T> first, MyArrayList<T> second,
                                        ArrayList<MyArrayList<T>> dataList, MyFunction<T> function) throws IOException {
        int size = dataList.size();
        for (int it = 0; it < size; ++it) {
            if (it % 2 == 0) {
                function.apply(dataList.get(it), first, second);
            }
            else {
                function.apply(dataList.get(it), second, first);
            }
        }

        FileWorker.writeFile(Validator.outPutFilePath, size % 2 == 0 ? first : second);
    }
}
