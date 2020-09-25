import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            Validator.validate(Parser.parseArgs(args));
            startRoutine();
        }
        catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }

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
            }
            else unsortedFiles.add(path);
        }

        System.out.println(sortedFiles + " " + unsortedFiles);

        if (Validator.options.contains("-s")) {
            String[] result;
        }

    }
}
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

//    //System.out.println(new Integer(1).compareTo(new Integer(2)));
//    Integer[] array1 = { 5, 6, 10, 11 };
//    Integer[] array2 = { 2, 3, 8, 9, 13 };
//        System.out.println(Arrays.toString(Sorter.<Integer>merge(array1, array2, new Integer[array1.length + array2.length])));
//
//        //System.out.println(" ".compareTo("rp"));
//        String[] str1 = {"a", "aag", "abc", "h", "lfqd", "ls", "x"};
//        String[] str2 = {" ", "a", "bca", "cs", "kill", "rp", "zd"};
//        System.out.println(Arrays.toString(Sorter.merge(str1, str2, new String[str1.length + str2.length])));
//
//        String[] str3 = {"x", "ls", "h", "abc", "a" };
//        String[] str4 = { "zd", "rp", "kill", "cs", "bca", " "};
//        System.out.println(Arrays.toString(Sorter.merge(str3, str4, new String[str3.length + str4.length])));

//        Integer[] arr = new Integer[]{20, 1, 13, 15, 127, 14, 5, 34, 67, 3, 1};
//        Sorter.sortInt(arr);
//        System.out.println(Arrays.toString(arr));
//
//        String[] strings = new String[]{"xs", "aee", "arr", "press", "f", "zz", "zzd", "ll", "l", "nds"};
//        Sorter.sortString(strings);
//        System.out.println(Arrays.toString(strings));

//        System.out.println(FileWorker.checkFileSort(Validator.inPutFilesPaths.get(0)) +
//                " " + FileWorker.checkFileSort(Validator.inPutFilesPaths.get(1)));