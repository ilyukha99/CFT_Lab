import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        try {
            Validator.validate(Parser.parseArgs(args));
            startRoutine();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

    public static void startRoutine() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Termination.")));
        if (Validator.options.contains("-d")) {
            Sorter.flag = -1;
        }

//        ArrayList<Path> sortedFiles = new ArrayList<>();
//        ArrayList<Path> unsortedFiles = new ArrayList<>();
//        for (Path path : Validator.inPutFilesPaths) {
//            if (FileWorker.checkFileSort(path)) {
//                sortedFiles.add(path);
//            } else unsortedFiles.add(path);
//        }

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(20, 1, 7, 18, 14, 7 , 8, 11, 6, 4, 3, 15, 2));
        Sorter.sortInt(list);
        System.out.println(list);

        ArrayList<String> strList = new ArrayList<>(Arrays.asList("a", "aag", "abc", "www", "h", "lfqd", "ls", "x",
                " ", "a", "bca", "cs", "kill", "rp", "yre", "zd", "ssd", "oracle"));
        Sorter.sortString(strList);
        System.out.println(strList);
    }
}
//        if (Validator.options.contains("-s")) {
//            String[] tmp = {};
//            for (int it = 0; sortedFiles.size() - it > 1; it += 2) {
//                String[] strings = Objects.requireNonNull(FileWorker.readFile(sortedFiles.get(it)).toArray(new String[1]));
//                String[] strings1 = Objects.requireNonNull(FileWorker.readFile(sortedFiles.get(it + 1)).toArray(new String[1]));
//                tmp = new String[strings.length + strings1.length];
//                Sorter.merge(strings, strings1, tmp);
//            }
//
//            String[] sortedResult = {};
//            if (sortedFiles.size() % 2 == 1) {
//                String[] strings = Objects.requireNonNull
//                        (FileWorker.readFile(sortedFiles.get(sortedFiles.size() - 1)).toArray(new String[1]));
//                sortedResult = new String[tmp.length + strings.length];
//                Sorter.merge(tmp, strings, sortedResult);
//            }
//            sortedResult = (sortedFiles.size() % 2 == 1) ? sortedResult : tmp;
//            Arrays.asList(sortedResult).forEach(System.out::println);
//
//            ArrayList<String> list = new ArrayList<>();
//            for (Path path : unsortedFiles) {
//                list.addAll(FileWorker.readFile(path));
//            }
//            String[] anotherResult = list.toArray(new String[1]);
//            Sorter.sortString(anotherResult);
//
//            if (sortedFiles.size() > 0 && unsortedFiles.size() > 0) {
//                FileWorker.writeFile(Validator.outPutFilePath, Sorter.merge(sortedResult,
//                        anotherResult, new String[sortedResult.length + anotherResult.length]));
//            }
//            else FileWorker.writeFile(Validator.outPutFilePath,
//                    (sortedFiles.size() == 0) ? anotherResult : sortedResult);
//        }

//        if (Validator.options.contains("-s")) {
//            ArrayList<String> list = new ArrayList<>();
//            for (Path path : Validator.inPutFilesPaths) {
//                list.addAll(FileWorker.readFile(path));
//            }
//
//            String[] result = list.toArray(new String[1]);
//            Sorter.sortString(result);
//            FileWorker.writeFile(Validator.outPutFilePath, result);
//        }

//        System.out.println(Arrays.toString(Sorter.<Integer>merge(array1, array2, new Integer[array1.length + array2.length])));
//
//        //System.out.println(" ".compareTo("rp"));
//        List<String> list = List.of(new String[]{"a", "aag", "abc", "h", "lfqd", "ls", "x"});
//        List<String> list1 = List.of(new String[]{" ", "a", "bca", "cs", "kill", "rp", "zd"});
//        System.out.println(Sorter.merge(list1, list, new ArrayList<>()));
//
//        List<Integer> int1 = List.of(new Integer[]{ 5, 6, 10, 11, 17, 24} );
//        List<Integer> int2 = List.of(new Integer[]{ 2, 4, 5, 7, 9, 18, 25} );
//        System.out.println(Sorter.merge(int1, int2, new ArrayList<>()));
//
//        String[] str3 = {"x", "ls", "h", "abc", "a" };
//        String[] str4 = { "zd", "rp", "kill", "cs", "bca", " "};
//        System.out.println(Arrays.toString(Sorter.merge(str3, str4, new String[str3.length + str4.length])));

//        Integer[] arr = new Integer[]{20, 1, 13, 15, 127, 14, 5, 34, 67, 3, 1};
//        Sorter.sortInt(arr);
//        System.out.println(Arrays.toString(arr));
//
//        List<String> str3 = List.of(new String[]{"x", "ls", "h", "abc", "a" });
//        List<String> str4 = List.of(new String[]{"zd", "rp", "kill", "cs", "bca", " " });
//        System.out.println(Sorter.merge(str3, str4, new ArrayList<>()));

//        System.out.println(FileWorker.checkFileSort(Validator.inPutFilesPaths.get(0)) +
//                " " + FileWorker.checkFileSort(Validator.inPutFilesPaths.get(1)));