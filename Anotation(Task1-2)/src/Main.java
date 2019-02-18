import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        TextContainer tc = new TextContainer();
        Class<?> cl = tc.getClass();
        SaveTo saveTo = cl.getAnnotation(SaveTo.class);
        if (saveTo == null){
            System.out.println("Class is not annotated");
            return;
        }
        Method[] methods = cl.getDeclaredMethods();
        for (Method method:methods) {
            if (method.isAnnotationPresent(Saver.class)){
                try {
                    method.invoke(tc, saveTo.path());
                } catch (IllegalAccessException |InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
