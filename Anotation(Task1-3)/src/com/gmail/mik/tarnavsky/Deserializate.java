package com.gmail.mik.tarnavsky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Deserializate {
    public static void tryToLoad(Object o, String path) throws IOException, IllegalAccessException{
        Class<?> cl = o.getClass();
        if(!cl.isAnnotationPresent(Save.class)){
            System.out.println("Class is not annotated (Load fail)");
        }
        HashMap<String,String> database = parseTheFile(path);
        Field[] fields = cl.getDeclaredFields();
        for (Field field:fields) {
            if (field.isAnnotationPresent(Save.class)){
                if (!field.isAccessible()){
                    field.setAccessible(true);
                }
                Class<?>fCls = field.getType();
                if (fCls == double.class){
                    field.setDouble(o, Double.parseDouble(database.get(field.getName())));
                }else if (fCls == int.class){
                    field.setInt(o, Integer.parseInt(database.get(field.getName())));
                }else if (fCls == boolean.class){
                    field.setBoolean(o, Boolean.parseBoolean(database.get(field.getName())));
                }else if (fCls == long.class){
                    field.setLong(o, Long.parseLong(database.get(field.getName())));
                }else if (fCls == String.class){
                    field.set(o, database.get(field.getName()));
                }else {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private static HashMap<String, String> parseTheFile(String path) throws IOException {
        HashMap<String,String> loadBase = new HashMap<>();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String line = bf.readLine();
        String[] temp;
        for (String pairs : line.split(";")){
            temp =pairs.split("=");
            loadBase.put(temp[0],temp[1]);
        }
        bf.close();
        return loadBase;
    }
}
