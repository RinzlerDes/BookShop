package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel
{
    private final SimpleStringProperty bookId;
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty author;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData)
    {
        bookId =  new SimpleStringProperty(bookData.elementAt(0));
        bookTitle =  new SimpleStringProperty(bookData.elementAt(1));
        author =  new SimpleStringProperty(bookData.elementAt(2));
        pubYear =  new SimpleStringProperty(bookData.elementAt(3));
        status =  new SimpleStringProperty(bookData.elementAt(4));
    }

    //----------------------------------------------------------------------------
    public String getBookNumber() {
        return bookId.get();
    }

    //----------------------------------------------------------------------------
    public void setBookNumber(String number) {
        bookId.set(number);
    }

    //----------------------------------------------------------------------------
    public String getBookTtile() {
        return bookTitle.get();
    }

    //----------------------------------------------------------------------------
    public void setBookTtile(String aType) {
        bookTitle.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String bal) {
        author.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getPubYear() {
        return pubYear.get();
    }

    //----------------------------------------------------------------------------
    public void setPubYear(String charge)
    {
        pubYear.set(charge);
    }

    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String charge)
    {
        status.set(charge);
    }
}