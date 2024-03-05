import model.Book;
import model.Patron;
import model.PatronCollection;
import model.BookCollection;
import exception.InvalidPrimaryKeyException;
import java.util.Properties;

public class BookShopTester {

    public static void main(String[] args) {
        try {
            // Test creating a book with existing ID
            Book book1 = new Book("1");
            System.out.println("Book 1 details:");
            System.out.println(book1);

//
//            Patron patron1 = new Patron("1");
//            System.out.println("Patron 1 details:");
//            System.out.println(patron1);
//
//            // Test creating a patron with new data
//            Properties newPatronData = new Properties();
//            newPatronData.setProperty("name", "John Doe");
//            newPatronData.setProperty("address", "123 Main St");
//            newPatronData.setProperty("city", "NY");
//            newPatronData.setProperty("stateCode", "NY");
//            newPatronData.setProperty("zip", "14658");
//            newPatronData.setProperty("email", "john@example.com");
//            newPatronData.setProperty("dateOfBirth", "1990-01-01");
//            newPatronData.setProperty("status", "ACTIVE");
//
//            Patron newPatron = new Patron(newPatronData);
//            System.out.println("New Patron:");
//            System.out.println(newPatron);
//
//            //Test updating a patron
//            newPatronData.setProperty("name", "ganghis khan" );
//            newPatron = new Patron(newPatronData);
//            newPatron.update();
//            System.out.println(newPatron);
//
//            // Test creating a book with new data
//            Properties newBookData = new Properties();
//            newBookData.setProperty("bookTitle", "New Book");
//            newBookData.setProperty("author", "Jane Doe");
//            newBookData.setProperty("pubYear", "2022");
//            Book newBook = new Book(newBookData);
//            System.out.println("New Book details:");
//            System.out.println(newBook);
//
//            // Test updating a book
//            newBookData.setProperty("bookTitle", "The Art of War");
//            newBook = new Book(newBookData);
//            newBook.update();
//            System.out.println("Updated Book details:");
//            System.out.println(newBook);
//
//            BookCollection bookCollection  = new BookCollection();
//            PatronCollection patronCollection = new PatronCollection();
//
//
//            try {
//                bookCollection.findBooksWithTitleLike("");
//                bookCollection.display();
//            }catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//
//            }
//
//            try {
//                bookCollection.findBooksOlderThanDate("2010");
//                bookCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
//
//            try {
//                bookCollection.findBooksNewerThanDate("2019");
//                bookCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
//
//            try
//            {
//                patronCollection.findPatronsOlderThan("1990");
//                patronCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
//
//            try
//            {
//                patronCollection.findPatronsYoungerThan("1990");
//                patronCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
//            try
//            {
//                patronCollection.findPatronsAtZipCode("14623");
//                patronCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
//
//            try
//            {
//                patronCollection.findPatronsWithNameLike("Sun Tzu");
//                patronCollection.display();
//            }
//            catch(InvalidPrimaryKeyException e) {
//                System.out.print("error");
//            }
        } catch (InvalidPrimaryKeyException e) {
            e.printStackTrace();
        }
    }
}
