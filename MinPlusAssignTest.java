package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinPlusAssignTest {
    @Test
    void testMin() {
        Long[] array = {5L, 3L, 2L, 4L, 1L};
        SegmentTree<Long, Long> st = new SegmentTree<>(
                array,
                MinCombinerAndUpdater.minCombiner(),
                MinCombinerAndUpdater.assignUpdater(),
                Long.MAX_VALUE,
                Long.MAX_VALUE
        );

        assertEquals(2L, st.query(1, 4));
        st.update(1, 3, 0L); // Присваиваем 0 элементам 1-2
        assertEquals(0L, st.query(1, 3));
    }
}