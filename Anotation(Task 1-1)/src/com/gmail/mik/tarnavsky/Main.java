package com.gmail.mik.tarnavsky;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Class<?> cl = Sample.class;
        Method[] methods = cl.getDeclaredMethods();
        for (Method method: methods) {
            if(method.isAnnotationPresent(Test.class)){
                Test testAnot = method.getAnnotation(Test.class);
                try {
                    method.invoke(null , testAnot.param1(), testAnot.param2() );
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
class Sample{
    @Test(param1 = "Working", param2 = "Hard")
    public static void sampleMethod(String param1, String param2){
        System.out.println(param1+" "+param2);
    }
}
