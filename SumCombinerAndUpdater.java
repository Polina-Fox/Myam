package segtrees;

public class SumCombinerAndUpdater {
    public static Combiner<Long> sumCombiner() {
        return (a, b) -> a + b;
    }

    public static Updater<Long, Long> addUpdater() {
        return new Updater<>() {
            @Override
            public Long apply(Long value, Long update, int len) {
                return value + update * len; // Прибавляем x ко всем элементам диапазона
            }

            @Override
            public Long compose(Long current, Long next) {
                return current + next; // Композиция: сумма обновлений
            }

            @Override
            public Long identity() {
                return 0L; // Нейтральный элемент для обновлений
            }
        };
    }
}

