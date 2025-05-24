package segtrees;

public class MinCombinerAndUpdater {
    public static Combiner<Long> minCombiner() {
        return (a, b) -> Math.min(a, b);
    }

    public static Updater<Long, Long> assignUpdater() {
        return new Updater<>() {
            @Override
            public Long apply(Long value, Long update, int len) {
                return update; // Присваиваем всем элементам диапазона значение x
            }

            @Override
            public Long compose(Long current, Long next) {
                return next; // Последнее обновление перезаписывает предыдущее
            }

            @Override
            public Long identity() {
                return Long.MAX_VALUE; // Нейтральный элемент (не влияет на минимум)
            }
        };
    }
}
