public class Sorter {
    public static int flag = 1; //1 if -a, -1 if -d

    public static void sortString(String[] array) throws NullPointerException{
        if (array == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (array.length == 1) {
            return;
        }

        String[] first = new String[array.length / 2];
        String[] second = new String[array.length - first.length];

        System.arraycopy(array, 0, first, 0, array.length / 2);
        System.arraycopy(array, array.length/2, second, 0, array.length - first.length);

        sortString(first);
        sortString(second);
        merge(first, second, array);
    }

    public static void sortInt(Integer[] array) {
        if (array == null) {
            throw new NullPointerException("An array seems to be null.");
        }

        if (array.length == 1) {
            return;
        }

        Integer[] first = new Integer[array.length / 2];
        Integer[] second = new Integer[array.length - first.length];

        System.arraycopy(array, 0, first, 0, array.length / 2);
        System.arraycopy(array, array.length/2, second, 0, array.length - first.length);

        sortInt(first);
        sortInt(second);
        merge(first, second, array);
    }

    public static <T extends Comparable<T>> T[] merge(T[] firstArr, T[] secondArr, T[] result) {
        int firstMin = 0, secondMin = 0, resultMin = 0;
        while (firstMin < firstArr.length && secondMin < secondArr.length) {
            if (firstArr[firstMin].compareTo(secondArr[secondMin]) * flag < 0) {
                result[resultMin] = firstArr[firstMin];
                ++firstMin;
            }
            else {
                result[resultMin] = secondArr[secondMin];
                ++secondMin;
            }
            ++resultMin;
        }

        if (firstMin == firstArr.length) {
            System.arraycopy(secondArr, secondMin, result, firstMin + secondMin, result.length - firstMin - secondMin);
        }
        else {
            System.arraycopy(firstArr, firstMin, result, firstMin + secondMin, result.length - firstMin - secondMin);
        }

        return result;
    }
}
