package com.magic.music;

import com.magic.music.utils.MyKeyGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

/**
 * @author laoma
 * @create 2021-03-11 11:14
 */
// @SpringBootTest
public class MyKeyGeneratorTest {

    // String s = o.toString()+":"+method.getName()+":"+ Arrays.toString(objects);

    @Autowired
    MyKeyGenerator myKeyGenerator;

    @Test
    void testString() throws NoSuchMethodException {
        String s = "%s%s%s";
        String format = String.format(s, "a", ":b", "c");
        System.out.println("format = " + format);
        Method method = this.getClass().getMethod(null);
        // System.out.println("method = " + method);
    }

    // @Test
    // void testKey() {
    //     Object generate = myKeyGenerator.generate(this,,);
    //     System.out.println("generate.toString() = " + generate.toString());
    // }
}
