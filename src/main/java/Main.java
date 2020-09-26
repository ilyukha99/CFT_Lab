public class Main {

    public static void main(String[] args) {
        try {
            Validator.validate(Parser.parseArgs(args));
            Worker.startRoutine();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
    }
}
//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(20, 1, 7, 18, 14, 7 , 8, 11, 6, 4, 3, 15, 2));
//        Sorter.sortInts(list);
//        System.out.println(list);
//
//        ArrayList<String> strList = new ArrayList<>(Arrays.asList("a", "aag", "abc", "www", "h", "lfqd", "ls", "x",
//                " ", "a", "bca", "cs", "kill", "rp", "yre", "zd", "ssd", "oracle"));
//        Sorter.sortStrings(strList);
//        System.out.println(strList);

//        System.out.println(Arrays.toString(Sorter.<Integer>merge(array1, array2, new Integer[array1.length + array2.length])));
//
//        //System.out.println(" ".compareTo("rp"));
//        MyArrayList<String> list = Stream.of("a", "aag", "abc", "h", "lfqd", "ls", "x").collect(Collectors.toCollection(MyArrayList::new));
//        MyArrayList<String> list1 = Stream.of(" ", "a", "bca", "cs", "kill", "rp", "zd").collect(Collectors.toCollection(MyArrayList::new));
//        System.out.println(Sorter.merge(list1, list, new MyArrayList<>(), 0));
//
//        List<Integer> int1 = List.of(new Integer[]{ 5, 6, 10, 11, 17, 24} );
//        List<Integer> int2 = List.of(new Integer[]{ 2, 4, 5, 7, 9, 18, 25} );
//        System.out.println(Sorter.merge(int1, int2, new ArrayList<>()));
//
//        List<String> str3 = List.of(new String[]{"x", "ls", "h", "abc", "a" });
//        List<String> str4 = List.of(new String[]{"zd", "rp", "kill", "cs", "bca", " " });
//        System.out.println(Sorter.merge(str3, str4, new ArrayList<>()));

//        System.out.println(FileWorker.checkFileSort(Validator.inPutFilesPaths.get(0)) +
//                " " + FileWorker.checkFileSort(Validator.inPutFilesPaths.get(1)));