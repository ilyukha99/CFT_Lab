public class Sorter {
    public static int flag = 1; //1 if -a, -1 if -d

    public static void sortStrings(MyArrayList<String> list) throws NullPointerException {
        if (list == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (list.size() == 1) {
            return;
        }

        MyArrayList<String> first = new MyArrayList<>();
        int size = list.size() / 2;
        for (int it = 0; it < size; ++it) {
            first.add(it, list.get(it));
        }

        MyArrayList<String> second = new MyArrayList<>();
        size = list.size() - list.size() / 2;
        for (int it = 0; it < size; ++it) {
            second.add(it, list.get(it + first.size()));
        }

        sortStrings(first);
        sortStrings(second);
        merge(first, second, list);
    }

    public static void sortInts(MyArrayList<Integer> list) {
        if (list == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (list.size() == 1) {
            return;
        }

        MyArrayList<Integer> first = new MyArrayList<>();
        int size = list.size() / 2;
        for (int it = 0; it < size; ++it) {
            first.add(it, list.get(it));
        }

        MyArrayList<Integer> second = new MyArrayList<>();
        size = list.size() - list.size() / 2;
        for (int it = 0; it < size; ++it) {
            second.add(it, list.get(it + first.size()));
        }

        sortInts(first);
        sortInts(second);
        merge(first, second, list);
    }

    public static <T extends Comparable<T>> void merge(MyArrayList<T> firstList,
                                                                 MyArrayList<T> secondList, MyArrayList<T> result) {
        int firstMin = 0, secondMin = 0, resultMin = 0;
        while (firstMin < firstList.size() && secondMin < secondList.size()) {
            if (firstList.get(firstMin).compareTo(secondList.get(secondMin)) * flag < 0) {
                result.set(resultMin, firstList.get(firstMin));
                ++firstMin;
            }
            else {
                result.set(resultMin, secondList.get(secondMin));
                ++secondMin;
            }
            ++resultMin;
        }

        if (firstMin == firstList.size()) {
            int size = firstMin + secondList.size();
            for (int it = firstMin + secondMin; it < size; ++it) {
                result.set(it, secondList.get(it - firstMin));
            }
        }
        else {
            int size = secondMin + firstList.size();
            for (int it = firstMin + secondMin; it < size; ++it) {
                result.set(it, firstList.get(it - secondMin));
            }
        }
    }
}
