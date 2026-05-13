package books;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class InMemoryDatabase implements Database {

    private final Map<Long, Book> books = new ConcurrentSkipListMap<>();

    public InMemoryDatabase() {
        Book book1 = new Book(1L, "A Startup Enxuta", "Eric Ries", "Editora Sextant", 2011);
        books.put(book1.id(), book1);

        Book book2 = new Book(2L, "O Lado Difícil das Situações Difíceis", "Ben Horowitz", "Editora Alta Books", 2014);
        books.put(book2.id(), book2);

        Book book3 = new Book(3L, "A Arte da Guerra", "Sun Tzu", "Editora Penguin-Companhia", 2005);
        books.put(book3.id(), book3);

        Book book4 = new Book(4L, "O Poder do Hábito", "Charles Duhigg", "Editora Objetiva", 2012);
        books.put(book4.id(), book4);

        Book book5 = new Book(5L, "Mindset: A Nova Psicologia do Sucesso", "Carol S. Dweck", "Editora Objetiva", 2006);
        books.put(book5.id(), book5);
    }

    @Override
    public List<Book> getLibrary() {
        return new LinkedList<>(books.values());
    }

    @Override
    public int getTotalBooks() {
        return this.books.size();
    }

    @Override
    public void addBook(Book book) {
        books.put(book.id(), book);
    }
}
