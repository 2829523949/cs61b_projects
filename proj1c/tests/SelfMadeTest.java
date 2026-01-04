import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelfMadeTest {
    @Test
    public void testCrossDequeEquals() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        lld.addLast(10);
        ad.addLast(10);
        // 如果你的 equals 写对了，这里应该通过
        assertTrue(lld.equals(ad));
    }
    @Test
    public void testIteratorWithList() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(10);
        lld.addLast(20);
        lld.addLast(30);

        // 我们可以创建一个标准 List 来对比
        java.util.List<Integer> expected = java.util.List.of(10, 20, 30);
        int index = 0;

        // 用 for-each 循环测试，这是对 iterator() 最好的调用
        for (Integer actual : lld) {
            assertEquals("元素顺序或值不匹配", expected.get(index), actual);
            index++;
        }
        assertEquals("遍历的元素数量不对", 3, index);
    }
}
