package otus.page;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import otus.domain.Author;
import otus.domain.Book;
import otus.domain.Genre;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;


    @Test
    void getBooksList() throws Exception {
        final var amountOfBooks = 1;
        final var bookList = new ArrayList<Book>(amountOfBooks);
        for (int i = 0; i < amountOfBooks; i++) {
            bookList.add(
                    new Book("RandomString.make()" + i, new Author("RandomString.make()" + i), new Genre("RandomString.make()" + i))
            );
        }
        given(bookService.getBooks())
                .willReturn(bookList);
        mockMvc.perform(get("/book/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>List of available books:</h1>")));
    }

    @Test
    void editBook() throws Exception {
        final var bookId = 1;
        given(bookService.getBookById(bookId))
                .willReturn(
                        Optional.of(
                                new Book("RandomString.make()" + bookId,
                                        new Author("RandomString.make()" + bookId),
                                        new Genre("RandomString.make()" + bookId))));
        mockMvc.perform(get("/book/edit?id=" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Edit book info:</h1>")));
    }

    @Test
    void addBook() throws Exception {
        final var amountOfAuthors = 1;
        final var authorList = new ArrayList<Author>(amountOfAuthors);
        authorList.add(new Author("RandomString.make()" + amountOfAuthors));
        given(authorService.getAuthors())
                .willReturn(authorList);
        final var amountOfGenres = 1;
        final var genresList = new ArrayList<Genre>(amountOfGenres);
        genresList.add(new Genre("RandomString.make()" + amountOfGenres));
        given(genreService.getGenres())
                .willReturn(genresList);
        mockMvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Add new book:</h1>")));
    }

    @Test
    void deleteBook() throws Exception {
        final var bookId = 1;
        final var amountOfBooks = 3;
        final var bookList = new ArrayList<Book>(amountOfBooks);
        for (int i = 0; i < amountOfBooks; i++) {
            bookList.add(
                    new Book("RandomString.make()" + i, new Author("RandomString.make()" + i), new Genre("RandomString.make()" + i))
            );
        }
        doNothing().when(bookService)
                .deleteBook(bookId);
        given(bookService.getBooks())
                .willReturn(bookList);
        mockMvc.perform(delete("/book/delete?id=" + bookId))
                .andExpect(status().isFound())
                .andExpect(content().string(""));
    }
}