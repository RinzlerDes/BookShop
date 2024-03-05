// specify the package
package model;
import model.Book;

// system imports
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

//import userinterface.View;
//import userinterface.ViewFactory;


/** The class containing the AccountCollection for the ATM application */
//==============================================================
public class BookCollection extends EntityBase implements IView
{
    private static final String myTableName = "book";
    private Vector<Book> bookList;

    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public BookCollection() {
        super(myTableName);
        bookList = new Vector<>();
    }

    private void addBook(Book b)
    {
        int index = findIndexTOAdd(b);
        bookList.insertElementAt(b, index);

    }

    private int findIndexTOAdd(Book b)
    {
        int low=0;
        int high=bookList.size()-1;
        int middle;

        while (low <= high)
        {
            middle = (low+high)/2;
            Book midSession = bookList.elementAt(middle);
            int result = Book.compare(b, midSession);
            if(result == 0)
            {
                return middle;
            }
            else if (result < 0)
            {
                high=middle-1;

            }
            else {
                low=middle+1;

            }
        }
        return low;
    }

    public void findBooksOlderThanDate(String year) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE pubYear < " +year;
        populateBookListWithQuery(query);

    }
    public void findBooksNewerThanDate(String year) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE pubYear > " + year;
        populateBookListWithQuery(query);
    }

    public void findBooksWithTitleLike(String title) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + title + "%'";
        populateBookListWithQuery(query);
    }

    public void findBooksWithAuthorLike(String author) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%'";
        populateBookListWithQuery(query);
    }


    private void populateBookListWithQuery(String query) throws InvalidPrimaryKeyException {
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties bookData = allDataRetrieved.elementAt(cnt);
                Book book = new Book(bookData);
                if (book != null) {
                    addBook(book);
                }
            }
        }
            else
            {
                throw new InvalidPrimaryKeyException("books not found");
            }
        }


    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Books"))
            return bookList;
        else
        if (key.equals("BookList"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    //----------------------------------------------------------
//    public Book retrieve(String bookId) {
//        for (Book book : bookList) {
//            if (book.getState(bookId).equals(bookId)) {
//                return book;
//            }
//        }
//        return null;
//    }
    public Book retrieve(String bookId) {

        Book retValue = null;
        for (int cnt = 0; cnt < bookList.size(); cnt++) {
            Book nextBook = bookList.elementAt(cnt);
            String nextBookNum = (String) nextBook.getState("bookId");
            if (nextBookNum.equals(bookId) == true) {
                retValue = nextBook;
                return retValue;
            }
        }

        return retValue;
    }



    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //------------------------------------------------------
//    protected void createAndShowView()
//    {
//
//        Scene localScene = myViews.get("AccountCollectionView");
//
//        if (localScene == null)
//        {
//            // create our new view
//            View newView = ViewFactory.createView("AccountCollectionView", this);
//            localScene = new Scene(newView);
//            myViews.put("AccountCollectionView", localScene);
//        }
//        // make the view visible by installing it into the frame
//        swapToView(localScene);
//
//    }

    //-----------------------------------------------------------------------------------

    public void display(){
        if (bookList.isEmpty())
        {
            System.out.println("no books");
            return;
        }
        for (int cnt = 0;cnt <bookList.size(); cnt++) {
            System.out.println(bookList.elementAt(cnt).toString());

        }
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}


