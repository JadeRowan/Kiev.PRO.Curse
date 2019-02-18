import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@SaveTo(path="..\\Anotation(Task1-2)\\text.txt")
public class TextContainer {
    private String text;

    public TextContainer() {
        this.text = "Waba-Laba-Dub-Da!";
    }

    public TextContainer(String text) {
        this.text = text;
    }

    @Saver
    public void save(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        try{
            fw.write(text);
        }
        finally {
            fw.close();
        }
    }
}
