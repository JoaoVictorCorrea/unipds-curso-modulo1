package books;

import java.io.Serializable;

public record Book(Long id, String name, String author, String publisher, int year) implements Serializable {
}
