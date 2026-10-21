package edu.unac.repository;

import edu.unac.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    private static BookRepository bookRepository;
    private static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb");
        connection.createStatement().execute(
                "CREATE TABLE books (id VARCHAR(255) PRIMARY KEY, title VARCHAR(255), borrowed_by VARCHAR(255));"
        );

        bookRepository = new BookRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.prepareStatement(
                "TRUNCATE TABLE books"
        ).executeUpdate();
    }

    @Test
    void saveBook() throws SQLException {
        //Arrange
        Book book = new Book("1", "Clean Code");

        //Act
        bookRepository.save(book);

        //Assert
        Optional<Book> response = bookRepository.findById("1");
        assertTrue(response.isPresent());
    }

    @Test
    void notFoundBook() throws SQLException {
        //Arrange
        Book book = new Book("2", "Scrum");

        bookRepository.save(book);

        //Act
        Optional<Book> response = bookRepository.findById("1");

        //Assert
        assertFalse(response.isPresent());
    }

    @Test
    void findAll() throws SQLException {
        //Arrange
        Book book = new Book("3", "Clean code");
        bookRepository.save(book);

        Book bookTwo = new Book("4", "Scrum");
        bookRepository.save(bookTwo);

        //Act
        List<Book> bookList = bookRepository.findAll();

        //Assert
        assertEquals(2, bookList.size());
    }

    @Test
    void findByTitle() throws SQLException {
        //Arrange
        Book book = new Book("1", "Software Usability 2");
        bookRepository.save(book);

        //Act
        Optional<Book> response = bookRepository.findByTitle("Software Usability 2");

        //Assert
        assertTrue(response.isPresent());
        assertEquals("Software Usability 2", response.get().getTitle());
    }

    @Test
    void findByTitleNotFound(){

    }
}