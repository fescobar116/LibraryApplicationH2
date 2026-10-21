package edu.unac.repository;

import edu.unac.domain.Book;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    private static BookRepository bookRepository;
    private static Connection connection;

    @Test
    void saveBook() throws SQLException {
        //Arrange
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        connection.createStatement().execute(
                "CREATE TABLE books (id VARCHAR(255) PRIMARY KEY, title VARCHAR(255), borrowed_by VARCHAR(255));"
        );

        bookRepository = new BookRepository(connection);

        Book book = new Book("1", "Clean Code");

        //Act
        bookRepository.save(book);

        //Assert
        Optional<Book> response = bookRepository.findById("1");
        assertTrue(response.isPresent());
    }
}