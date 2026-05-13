package books;

import java.util.List;

public interface Database {
    List<Book> getLibrary();

    void addBook(Book book);

    int getTotalBooks();
}
