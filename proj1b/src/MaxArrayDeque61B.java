import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    private Comparator c = Comparator.naturalOrder();
    public MaxArrayDeque61B(Comparator<T> c){
        this.c = c;
    }

    public T max() {
        if(size() <= 0)
            return null;
        T max = get(0);

        for (T x : this) {
            if (c.compare(max, x) > 0) {
                max = x;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        return null;
    }
}
