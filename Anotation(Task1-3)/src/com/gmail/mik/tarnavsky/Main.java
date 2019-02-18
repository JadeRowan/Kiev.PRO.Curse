package com.gmail.mik.tarnavsky;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Cat felix = new Cat(13, "Felix", 8, 10.7, "Grey", true);
        Cat someCat = new Cat();
        String path = "src\\base.txt";
        try {
            Serializate.tryToSave(felix, path);
            Deserializate.tryToLoad(someCat, path);
        } catch (IllegalAccessException | IOException e ) {
            e.printStackTrace();
        }
        System.out.println(someCat);

    }
}
