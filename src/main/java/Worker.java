import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;

public class Worker {

    public static void startRoutine() throws IOException {

        ArrayList<Path> sortedFiles = new ArrayList<>();
        ArrayList<Path> unsortedFiles = new ArrayList<>();
        for (Path path : Validator.inPutFilesPaths) {
            if (Files.size(path) > 0) {
                if (FileWorker.checkFileSort(path)) {
                    sortedFiles.add(path);
                } else if (Files.size(path) > 0) unsortedFiles.add(path);
            }
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
            Sorter.sort(lines);
            dataList.add(lines);
        }

        Optional<Integer> optSize = dataList.stream().map(ArrayList::size).reduce(Integer::sum);
        if (optSize.isEmpty()) {
            System.out.println("The specified files are empty.");
            return;
        }

        endRoutine(dataList, Sorter::merge);
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
            Sorter.sort(lines);
            dataList.add(lines);
        }

        Optional<Integer> optSize = dataList.stream().map(ArrayList::size).reduce(Integer::sum);
        if (optSize.isEmpty()) {
            System.out.println("The specified files are empty.");
            return;
        }

        endRoutine(dataList, Sorter::merge);
    }

    public static <T extends Comparable<T>> void endRoutine(ArrayList<MyArrayList<T>> dataList,
                                                            MyFunction<T> function) throws IOException {
        MyArrayList<T> first = new MyArrayList<>(), second = new MyArrayList<>();
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

    public static void workWithBigFiles() throws IOException {

        long systemFreeMem = Runtime.getRuntime().freeMemory() - 2_097_152; // -2Mb (correction)
        ArrayList<Path> bigFiles = new ArrayList<>(), smallFilesPath = new ArrayList<>();
        for (Path path : Validator.inPutFilesPaths) {
            if (Files.size(path) > systemFreeMem * 0.1) { //with coefficient
                bigFiles.add(path);
            }
            else if (Files.size(path) > 0) smallFilesPath.add(path);
        }

        ArrayList<Scanner> scanners = new ArrayList<>();
        Iterator<Path> iterator = bigFiles.iterator();
        while (iterator.hasNext()) {
            Path path = iterator.next();
            if (!FileWorker.checkFileSort(path)) {
                iterator.remove();
                System.out.println("File " + path + "will not be merged. Cause: big weight.");
            }
            else scanners.add(new Scanner(path).useDelimiter("\\n"));
        }

        BufferedWriter writer = FileWorker.getWriter(Validator.outPutFilePath);
        if (writer == null) {
            System.out.println("Can't get an output file writer.");
            return;
        }

        if (Validator.options.contains("-i")) {
            bigFilesIntRoutine(scanners, writer, smallFilesPath);
        }
        else BigFilesStringRoutine(scanners, writer, smallFilesPath);
    }

    public static void bigFilesIntRoutine(ArrayList<Scanner> scanners, BufferedWriter writer,
                                          ArrayList<Path> smallFilesPaths) throws IOException {
        Iterator<Scanner> scannerIterator = scanners.iterator();

        ArrayList<ArrayList<Integer>> smallFiles = new ArrayList<>();
        for (Path path : smallFilesPaths) {
            MyArrayList<Integer> ints = FileWorker.readFile(path)
                    .stream().map(Integer::parseInt)
                    .collect(Collectors.toCollection(MyArrayList::new));

            if (!FileWorker.checkFileSort(path)) {
                Sorter.sort(ints);
            }
            smallFiles.add(ints);
        }

        MyArrayList<Integer> buffer = new MyArrayList<>();
        for (int it = 0; scannerIterator.hasNext(); ++it) {
            Scanner scanner = scannerIterator.next();
            buffer.set(it, scanner.nextInt());
        }
        int forScanners = scanners.size(), forSmallFiles = smallFiles.size();

        for (int it = 0; it < forSmallFiles; ++it) {
            buffer.set(forScanners + it, smallFiles.get(it).get(0));
        }

        while (smallFiles.size() > 0 || scanners.size() > 0) {
            int extremum = findExtremumInd(buffer); //an index of the largest/smallest element
            writer.write(buffer.get(extremum) + "\n");
            if (extremum < scanners.size()) {
                Scanner curScanner = scanners.get(extremum);
                if (curScanner.hasNext()) {
                    buffer.set(extremum, curScanner.nextInt());
                }
                else {
                    scanners.remove(curScanner);
                    buffer.remove(buffer.get(extremum));
                }
            }
            else {
                ArrayList<Integer> curList = smallFiles.get(extremum - scanners.size());
                curList.remove(buffer.get(extremum));
                if (curList.size() > 0) {
                    buffer.set(extremum, curList.get(0));
                }
                else {
                    smallFiles.remove(curList);
                    buffer.remove(buffer.get(extremum));
                }
            }
        }
        writer.close();
    }

    public static void BigFilesStringRoutine(ArrayList<Scanner> scanners, BufferedWriter writer,
                                             ArrayList<Path> smallFilesPaths) throws IOException {
        Iterator<Scanner> scannerIterator = scanners.iterator();

        ArrayList<ArrayList<String>> smallFiles = new ArrayList<>();
        for (Path path : smallFilesPaths) {
            MyArrayList<String> strings = FileWorker.readFile(path);

            if (!FileWorker.checkFileSort(path)) {
                Sorter.sort(strings);
            }
            smallFiles.add(strings);
        }

        MyArrayList<String> buffer = new MyArrayList<>();
        for (int it = 0; scannerIterator.hasNext(); ++it) {
            Scanner scanner = scannerIterator.next();
            buffer.set(it, scanner.next());
        }
        int forScanners = scanners.size(), forSmallFiles = smallFiles.size();

        for (int it = 0; it < forSmallFiles; ++it) {
            buffer.set(forScanners + it, smallFiles.get(it).get(0));
        }

        while (smallFiles.size() > 0 || scanners.size() > 0) {
            int extremum = findExtremumInd(buffer); //an index of the largest/smallest element
            writer.write(buffer.get(extremum) + "\n");
            if (extremum < scanners.size()) {
                Scanner curScanner = scanners.get(extremum);
                if (curScanner.hasNext()) {
                    buffer.set(extremum, curScanner.next());
                }
                else {
                    scanners.remove(curScanner);
                    buffer.remove(buffer.get(extremum));
                }
            }
            else {
                ArrayList<String> curList = smallFiles.get(extremum - scanners.size());
                curList.remove(buffer.get(extremum));
                if (curList.size() > 0) {
                    buffer.set(extremum, curList.get(0));
                }
                else {
                    smallFiles.remove(curList);
                    buffer.remove(buffer.get(extremum));
                }
            }
        }
        writer.close();
    }

    public static <T extends Comparable<T>> int findExtremumInd(MyArrayList<T> buffer) {
        T element = buffer.get(0);
        int size = buffer.size(), index = 0;
        for (int it = 1; it < size; ++it) {
            T next = buffer.get(it);
            if (element.compareTo(next) * Sorter.flag <= 0) {
                continue;
            }
            element = next;
            index = it;
        }
        return index;
    }

    //might be more safe to use (uses Iterator.remove())
    public static <T> void removeFromList(ArrayList<T> list, T value) {
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            if (!iter.next().equals(value)) {
                continue;
            }
            iter.remove();
            return;
        }
        list.remove(value);
    }
}