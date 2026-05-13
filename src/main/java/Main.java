import books.Book;
import books.Database;
import books.SQLDatabase;

void main() {
    Database database = new SQLDatabase();

    List<Book> books = database.getLibrary();
    books.forEach(System.out::println);

    int total = database.getTotalBooks();
    System.out.println(total);

    Book newBook = new Book(1L, "O Senhor dos Anéis", "J.R.R. Tolkien", "HarperCollins", 1954);
    database.addBook(newBook);

    books = database.getLibrary();
    books.forEach(System.out::println);
}
