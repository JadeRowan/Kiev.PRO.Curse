package com.gmail.mik.tarnavsky;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Serializate {
    public static void tryToSave(Object o, String path) throws IllegalAccessException, IOException{
        Class<?> cl = o.getClass();
        if(!cl.isAnnotationPresent(Save.class)){
            System.out.println("Class is not anotated (Save fail)");
        }
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
                SaveInFile(fieldName,fieldValue,path);
            }
        }
    }

    private static void SaveInFile(String fieldName, String fieldValue, String path) throws IOException {
        FileWriter fw =new FileWriter(path, true);
        try {
            fw.write(fieldName +"="+ fieldValue + ";");
        }
        finally {
            fw.close();
        }
    }
}
