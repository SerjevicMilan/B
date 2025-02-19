import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{
    private Comparator<T> c = (Comparator<T>) Comparator.naturalOrder();
    public MaxArrayDeque61B(Comparator<T> c){
        if(c != null)
            this.c = c;
    }

    public T max() {
        return max(c);
    }

    public T max(Comparator<T> c) {
        if(size() <= 0)
            return null;
        if(c == null)
            c = this.c;

        T max = get(0);
        for (T x : this) {
            if (c.compare(x, max) > 0) {
                max = x;
            }
        }
        return max;
    }
}
