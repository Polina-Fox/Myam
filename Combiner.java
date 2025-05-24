
package segtrees;

@FunctionalInterface
public interface Combiner<T> {
    T combine(T left, T right);
}

