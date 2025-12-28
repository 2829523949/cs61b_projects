import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
     @Test
    /**这个测试是用来测试两个add函数的以及tolist。要考虑扩容和循环的情况。干脆一起测试算了**/
    public void addTest(){
        Deque61B<Integer>test=new ArrayDeque61B<>();
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(4);
        test.addFirst(5);
        test.addFirst(6);
        test.addFirst(7);
        test.addLast(8);
        assertThat(test.size()==8);
        List<Integer>expected=List.of(7,6,5,4,3,2,1,8);
        assertThat(test.toList()).isEqualTo(expected);//List.of()可以直接创建数组
     }
     @Test
    /**这个测试是用来测试两个remove函数的**/
    public void testremove() {
         Deque61B<Integer> test = new ArrayDeque61B<>();
         test.addFirst(1);
         test.addLast(2);
         test.addLast(3);
         test.removeLast();
         test.removeFirst();
         assertThat(test.size()==1);
         List<Integer>expected=List.of(2);
         assertThat(test.toList()).isEqualTo((expected));
     }}
