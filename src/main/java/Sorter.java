import java.util.List;
import java.util.ArrayList;

public class Sorter {
    public static int flag = 1; //1 if -a, -1 if -d

    public static void sortString(List<String> list) throws NullPointerException{
        if (list == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (list.size() == 1) {
            return;
        }

        List<String> first = new ArrayList<>();
        int size = list.size() / 2;
        for (int it = 0; it < size; ++it) {
            first.add(it, list.get(it));
        }

        List<String> second = new ArrayList<>();
        size = list.size() - list.size() / 2;
        for (int it = 0; it < size; ++it) {
            second.add(it, list.get(it + first.size()));
        }

        sortString(first);
        sortString(second);
        merge(first, second, list);
    }

    public static void sortInt(List<Integer> list) {
        if (list == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (list.size() == 1) {
            return;
        }

        List<Integer> first = new ArrayList<>();
        int size = list.size() / 2;
        for (int it = 0; it < size; ++it) {
            first.add(it, list.get(it));
        }

        List<Integer> second = new ArrayList<>();
        size = list.size() - list.size() / 2;
        for (int it = 0; it < size; ++it) {
            second.add(it, list.get(it + first.size()));
        }

        sortInt(first);
        sortInt(second);
        merge(first, second, list);
    }

    public static <T extends Comparable<T>> List<T> merge(List<T> firstList, List<T> secondList, List<T> result) {
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
        return result;
    }
}
