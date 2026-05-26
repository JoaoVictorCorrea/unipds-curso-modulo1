package pix;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PixDeserializer {

    static void main() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream("pix.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Pix pix = (Pix) ois.readObject();
            System.out.println(pix);
            System.out.println(pix.getKey());
        }
    }
}
