package segtrees;

import java.util.Arrays;

public class SegmentTree<T, U> {
    private final int n;
    private final T[] tree;
    private final U[] lazy;
    private final Combiner<T> combiner;
    private final Updater<T, U> updater;
    private final T neutralAggregate;
    private final U neutralUpdate;

    public SegmentTree(T[] array, Combiner<T> combiner, Updater<T, U> updater, T neutralAggregate, U neutralUpdate) {
        this.n = array.length;
        this.combiner = combiner;
        this.updater = updater;
        this.neutralAggregate = neutralAggregate;
        this.neutralUpdate = neutralUpdate;
        int size = 1;
        while (size < n) size <<= 1;
        tree = (T[]) new Object[2 * size];
        lazy = (U[]) new Object[2 * size];
        Arrays.fill(lazy, neutralUpdate);
        build(array, 0, 0, size);
    }

    private void build(T[] array, int node, int nodeLeft, int nodeRight) {
        if (nodeRight - nodeLeft == 1) {
            tree[node] = (nodeLeft < n) ? array[nodeLeft] : neutralAggregate;
        } else {
            int mid = (nodeLeft + nodeRight) / 2;
            build(array, 2 * node + 1, nodeLeft, mid);
            build(array, 2 * node + 2, mid, nodeRight);
            tree[node] = combiner.combine(tree[2 * node + 1], tree[2 * node + 2]);
        }
    }

    public T query(int l, int r) {
        return query(0, 0, (1 << (32 - Integer.numberOfLeadingZeros(n))), l, r);
    }

    private T query(int node, int nodeLeft, int nodeRight, int l, int r) {
        applyPending(node, nodeLeft, nodeRight);
        if (nodeRight <= l || r <= nodeLeft) return neutralAggregate;
        if (l <= nodeLeft && nodeRight <= r) return tree[node];
        int mid = (nodeLeft + nodeRight) / 2;
        return combiner.combine(
                query(2 * node + 1, nodeLeft, mid, l, r),
                query(2 * node + 2, mid, nodeRight, l, r)
        );
    }

    public void update(int l, int r, U value) {
        update(0, 0, (1 << (32 - Integer.numberOfLeadingZeros(n))), l, r, value);
    }

    private void update(int node, int nodeLeft, int nodeRight, int l, int r, U value) {
        applyPending(node, nodeLeft, nodeRight);
        if (nodeRight <= l || r <= nodeLeft) return;
        if (l <= nodeLeft && nodeRight <= r) {
            lazy[node] = updater.compose(lazy[node], value);
            applyPending(node, nodeLeft, nodeRight);
            return;
        }
        int mid = (nodeLeft + nodeRight) / 2;
        update(2 * node + 1, nodeLeft, mid, l, r, value);
        update(2 * node + 2, mid, nodeRight, l, r, value);
        tree[node] = combiner.combine(tree[2 * node + 1], tree[2 * node + 2]);
    }

    private void applyPending(int node, int nodeLeft, int nodeRight) {
        if (lazy[node] == neutralUpdate) return;

        // Применяем обновление к текущему узлу
        int len = nodeRight - nodeLeft;
        tree[node] = updater.apply(tree[node], lazy[node], len);

        // Передаем обновление дочерним узлам, если они есть
        if (node < tree.length / 2) {
            lazy[2 * node + 1] = updater.compose(lazy[2 * node + 1], lazy[node]);
            lazy[2 * node + 2] = updater.compose(lazy[2 * node + 2], lazy[node]);
        }

        // Сбрасываем обновление
        lazy[node] = neutralUpdate;
    }
}
