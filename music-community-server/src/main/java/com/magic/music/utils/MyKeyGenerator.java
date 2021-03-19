package com.magic.music.utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author laoma
 * @create 2021-02-28 15:14
 */
@Component
public class MyKeyGenerator implements KeyGenerator {


    @Override
    public Object generate(Object o, Method method, Object... objects) {


        String s = "%s%s%s";
        String format = String.format(s, o.toString(), ":"+method.toString(), ":"+Arrays.toString(objects));

        // String s = o.toString()+":"+method.getName()+":"+ Arrays.toString(objects);
        return format;
    }
}
