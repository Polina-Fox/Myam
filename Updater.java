package segtrees;

@FunctionalInterface
public interface Updater<U> {
    U compose(U current, U update);
    default U identity() {
        return null; // Нейтральный элемент должен быть задан в конкретной реализации
    }
}

