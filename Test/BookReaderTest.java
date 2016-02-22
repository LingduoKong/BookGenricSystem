package Test;

import org.junit.*;
import com.book.genre.*;

/**
 * Created by lingduokong on 2/18/16.
 */
public class BookReaderTest {

    BookReader bookReader;

    @Before
    public void setUp() throws Exception {
        bookReader = new BookReader();
        bookReader.readInBooks("book.txt");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testReadInBooks() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        String correctResult = "{\"a\":{\"Hunger Games\":[2]},\"not\":{\"Hunger Games\"" +
                        ":[3]},\"in\":{\"Hunger Games\":[1]},\"too\":{\"Hunger Games\":" +
                        "[4]},\"distant\":{\"Hunger Games\":[5]}}";
        Assert.assertEquals (correctResult, bookReader.toString());
    }
}