import java.util.ArrayList;

public class MyArrayList<E> extends ArrayList<E> {
    @Override
    public E set(int index, E element) {
        if (this.size() <= index) {
            super.add(index, element);
        } else {
            super.set(index, element);
        }
        return element;
    }
}
