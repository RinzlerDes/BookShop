// specify the package
package model;
import model.Patron;

// system imports
import java.util.Properties;
import java.util.Vector;

//import com.sun.java.swing.plaf.windows.TMSchema;
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
public class PatronCollection extends EntityBase implements IView
{
    private static final String myTableName = "patron";
    private Vector<Patron> patronList;

    // GUI Components

    // constructor for this class
    //----------------------------------------------------------
    public PatronCollection() {
        super(myTableName);
        patronList = new Vector<>();
    }


    private void addPatron(Patron p)
    {
        int index = findIndexTOAdd(p);
        patronList.insertElementAt(p, index);

    }

    private int findIndexTOAdd(Patron p)
    {
        int low=0;
        int high=patronList.size()-1;
        int middle;

        while (low <= high)
        {
            middle = (low+high)/2;
            Patron midSession = patronList.elementAt(middle);
            int result = Patron.compare(p, midSession);
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



    public void findPatronsOlderThan(String date) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE dateOfBirth < '" + date + "'";
        populatePatronListWithQuery(query);

    }

    public void findPatronsYoungerThan(String date) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE dateOfBirth > '" + date + "'";
        populatePatronListWithQuery(query);

    }
    public void findPatronsAtZipCode(String zip) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE zip = '" + zip + "'";
        populatePatronListWithQuery(query);
    }

    public void findPatronsWithNameLike(String name) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE name LIKE '%" + name + "%'";
        populatePatronListWithQuery(query);
    }


    private void populatePatronListWithQuery(String query) throws InvalidPrimaryKeyException {
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties patronData = allDataRetrieved.elementAt(cnt);
                Patron patron = new Patron(patronData);
                patronList.add(patron);
            }
        }
    }

    /**
     *
     */
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("Patrons"))
            return patronList;
        else
        if (key.equals("patronCollection"))
            return this;
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

   //
   // ----------------------------------------------------------
    public Patron retrieve(String patronId) {

        Patron retValue = null;
        for (int cnt = 0; cnt < patronList.size(); cnt++) {
            Patron nextPatron = patronList.elementAt(cnt);
            String nextPatronNum = (String) nextPatron.getState("patronId");
            if (nextPatronNum.equals(patronId) == true) {
                retValue = nextPatron;
                return retValue;
            }
        }

        return retValue;
    }

    public void display(){
        if (patronList.isEmpty())
        {
            System.out.println("no patrons");
            return;
        }
        for (int cnt = 0;cnt <patronList.size(); cnt++) {
            System.out.println(patronList.elementAt(cnt).toString());

        }
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
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
