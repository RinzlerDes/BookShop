// specify the package
package model;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;

import impresario.IView;

//import userinterface.View;
//import userinterface.ViewFactory;

/** The class containing the Account for the ATM application */
//==============================================================
public class Patron extends EntityBase implements IView
{
    private static final String myTableName = "Patron";

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Patron(String patronId)
            throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";
        @SuppressWarnings("unchecked")

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);



        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one patron. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple patronIds matching : "
                        + patronId + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedAccountData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedAccountData.propertyNames();
                while (allKeys.hasMoreElements())
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedAccountData.getProperty(nextKey);
                    // accountNumber = Integer.parseInt(retrievedAccountData.getProperty("accountNumber"));

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No patron matching id : "
                    + patronId + " found.");
        }
    }

    // Can also be used to create a NEW Account (if the system it is part of
    // allows for a new account to be set up)
    //----------------------------------------------------------
    public Patron(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    /**
     * Verify ownership
     */
    //----------------------------------------------------------
//    public boolean verifyOwnership(AccountHolder cust)
//    {
//        if (cust == null)
//        {
//            return false;
//        }
//        else
//        {
//            String custid = (String)cust.getState("ID");
//            String myOwnerid = (String)getState("OwnerID");
//            // DEBUG System.out.println("Account: custid: " + custid + "; ownerid: " + myOwnerid);
//
//            return (custid.equals(myOwnerid));
//        }
//    }

    /**
     * Credit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
     */
    //----------------------------------------------------------
//    public void credit(String amount)
//    {
//        String myBalance = (String)getState("Balance");
//        double myBal = Double.parseDouble(myBalance);
//
//        double incrementAmount = Double.parseDouble(amount);
//        myBal += incrementAmount;
//
//        persistentState.setProperty("Balance", ""+myBal);
//    }

    /**
     * Debit balance (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
     */
    //----------------------------------------------------------
//    public void debit(String amount)
//    {
//        String myBalance = (String)getState("Balance");
//        double myBal = Double.parseDouble(myBalance);
//
//        double incrementAmount = Double.parseDouble(amount);
//        myBal -= incrementAmount;
//
//        persistentState.setProperty("Balance", ""+myBal);
//    }

    /**
     * Check balance -- returns true/false depending on whether
     * there is enough balance to cover withdrawalAmount or not
     * (Method is public because it may be invoked directly as it has no possibility of callback associated with it)
     *
     */
    //----------------------------------------------------------
//    public boolean checkBalance(String withdrawalAmount)
//    {
//        String myBalance = (String)getState("Balance");
//        double myBal = Double.parseDouble(myBalance);
//
//        double checkAmount = Double.parseDouble(withdrawalAmount);
//
//        if (myBal >= checkAmount)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
//
//    //----------------------------------------------------------
//    public void setServiceCharge(String value)
//    {
//        persistentState.setProperty("ServiceCharge", value);
//        updateStateInDatabase();
//    }

    //-----------------------------------------------------------------------------------
    public static int compare(Patron a, Patron b)
    {
        String aNum = (String)a.getState("patronId");
        String bNum = (String)b.getState("patronId");

        return aNum.compareTo(bNum);
    }

    //-----------------------------------------------------------------------------------
    public void update() // save()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("patronId") != null)
            {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("patronId",
                        persistentState.getProperty("patronId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "patron data for patronId : " + persistentState.getProperty("patronId") + " updated successfully in database!";
            }
            else
            {
                // insert
                Integer patronId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronId", "" + patronId.intValue());
                updateStatusMessage = "Book data for new book : " +  persistentState.getProperty("patronId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing account data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }


    /**
     * This method is needed solely to enable the Account information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();


        v.addElement(persistentState.getProperty("name"));
        v.addElement(persistentState.getProperty("address"));
        v.addElement(persistentState.getProperty("city"));
        v.addElement(persistentState.getProperty("stateCode"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("dateOfBirth"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    public String toString()
    {
        return "name: " + persistentState.getProperty("name") +
                "; address: " + persistentState.getProperty("address") +
                "; city: " + persistentState.getProperty("city") +
                "; zip: " + persistentState.getProperty("stateCode") +
                "; email: " + persistentState.getProperty("email") +
                "; dateOfBirth: " + persistentState.getProperty("dateOfBirth") +
                "; status: " + persistentState.getProperty("status");

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
}

