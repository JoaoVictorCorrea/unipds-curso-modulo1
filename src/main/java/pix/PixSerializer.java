package pix;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.Instant;

public class PixSerializer {

    static void main() throws IOException {
        Pix pix = new Pix(1L, new BigDecimal("10.99"), "joao.correa@gmail.com", Instant.now(), "Pagamento de teste");

        try (FileOutputStream fos = new FileOutputStream("pix.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(pix);
        }
    }
}
