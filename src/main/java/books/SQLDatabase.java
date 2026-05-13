package books;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase implements Database{

    @Override
    public List<Book> getLibrary() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, name, author, publisher, year FROM book";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8", "root", "senha123");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while(rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int year = rs.getInt("year");

                Book book = new Book(id, name, author, publisher, year);
                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) as total FROM book";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8", "root", "senha123");
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            if(rs.next()) {
                return rs.getInt("total");
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBook(Book book) {
        String sql = "INSERT INTO book (name, author, publisher, year) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8", "root", "senha123");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.name());
            preparedStatement.setString(2, book.author());
            preparedStatement.setString(3, book.publisher());
            preparedStatement.setInt(4, book.year());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
