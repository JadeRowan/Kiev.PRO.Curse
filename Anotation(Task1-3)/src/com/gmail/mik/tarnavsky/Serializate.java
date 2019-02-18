package com.gmail.mik.tarnavsky;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class Serializate {
    public static void tryToSave(Object o, String path) throws IllegalAccessException, IOException{
        Class<?> cl = o.getClass();
        if(!cl.isAnnotationPresent(Save.class)){
            System.out.println("Class is not anotated (Save fail)");
        }
        String text = "";
        Field[] fields = cl.getDeclaredFields();

        for (Field field: fields) {
            if (field.isAnnotationPresent(Save.class)){
                if (!field.isAccessible()){
                    field.setAccessible(true);
                }
                String fieldName = field.getName();
                String fieldValue;
                Class<?> type = field.getType();
                if (type == double.class){
                    fieldValue = String.valueOf(field.getDouble(o));
                }else if (type == int.class){
                    fieldValue = String.valueOf(field.getInt(o));
                }else if (type == boolean.class){
                    fieldValue = String.valueOf(field.getBoolean(o));
                }else if (type == long.class){
                    fieldValue = String.valueOf(field.getLong(o));
                }else {
                    fieldValue = String.valueOf(field.get(o));
                }
                text = text + fieldName + "=" + fieldValue + ";";
            }
        }
        if (!text.equals("")) {
            SaveInFile(text, path);
        }
    }

    private static void SaveInFile(String text, String path) throws IOException {
        FileWriter fw =new FileWriter(path);
        try {
            fw.write(text);
        }
        finally {
            fw.close();
        }
    }
}
