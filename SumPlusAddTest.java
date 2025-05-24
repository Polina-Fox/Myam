package segtrees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SumPlusAddTest {
    @Test
    void testSum() {
        Long[] array = {1L, 2L, 3L, 4L, 5L};
        SegmentTree<Long, Long> st = new SegmentTree<>(
                array,
                SumCombinerAndUpdater.sumCombiner(),
                SumCombinerAndUpdater.addUpdater(),
                0L,
                0L
        );

        assertEquals(9L, st.query(1, 4));
        st.update(0, 5, 3L);
        assertEquals(15L + 3L*5, st.query(0, 5)); // 1+3 + 2+3 + ... +5+3 = 15 + 5*3 = 30
    }
}
