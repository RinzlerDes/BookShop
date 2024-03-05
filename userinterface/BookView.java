// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;
import model.Book;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class BookView extends View
{

	// GUI components
	protected TextField bookId;
	protected TextField bookTitle;
	protected TextField author;
	protected TextField pubYear;
	protected ComboBox status;

	protected Button cancelButton;
	protected Button submitButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public BookView(IModel book)
	{
		super(book, "BookView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

        // These need to be made specific for Book
		//myModel.subscribe("ServiceCharge", this);
		//myModel.subscribe("UpdateStatusMessage", this);
	}


	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Library ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("Book INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);

		// Text bookIdLabel = new Text(" Book ID : ");
		// bookIdLabel.setFont(myFont);
		// bookIdLabel.setWrappingWidth(150);
		// bookIdLabel.setTextAlignment(TextAlignment.RIGHT);
		// grid.add(bookIdLabel, 0, 1);
		//
		// bookId = new TextField();
		// bookId.setEditable(false);
		// grid.add(bookId, 1, 1);

		Text bookTitleLabel = new Text(" Book Title : ");
		bookTitleLabel.setFont(myFont);
		bookTitleLabel.setWrappingWidth(150);
		bookTitleLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(bookTitleLabel, 0, 1);

		bookTitle = new TextField();
		bookTitle.setEditable(true);
		grid.add(bookTitle, 1, 1);

		Text authorLabel = new Text(" Author : ");
		authorLabel.setFont(myFont);
		authorLabel.setWrappingWidth(150);
		authorLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(authorLabel, 0, 2);

		author = new TextField();
		author.setEditable(true);
		grid.add(author, 1, 2);

		Text pubYearLabel = new Text(" Publication Year : ");
		pubYearLabel.setFont(myFont);
		pubYearLabel.setWrappingWidth(150);
		pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(pubYearLabel, 0, 3);

		pubYear = new TextField();
		pubYear.setEditable(true);
		pubYear.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  		    	myModel.stateChangeRequest("ServiceCharge", pubYear.getText());
       	     }
        });
		grid.add(pubYear, 1, 3);


		Text statusLabel = new Text(" Status : ");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 4);

		status = new ComboBox<String>();
		status.setMinSize(100, 20);
		grid.add(status, 1, 4);

		// status = new TextField();
		// status.setEditable(false);
		// grid.add(status, 1, 3);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("LibraryOptions", null);   
            	  }
        	});
		doneCont.getChildren().add(cancelButton);

		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				processAction(actionEvent);
				//myModel.stateChangeRequest("LibraryOptions", null);
			}
		});
		doneCont.getChildren().add(submitButton);


	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// Process user input
	public void processAction(Event event) {
		if(bookTitle.getText() == null || bookTitle.getText().length() == 0) {
			displayErrorMessage("Please enter a book title.");
			bookTitle.requestFocus();
		}
		else if(author.getText() == null || author.getText().length() == 0) {
			displayErrorMessage("Please enter an author.");
			author.requestFocus();
		}
		// needs fixing ---------------------------------------------------------------------------------------------
		else if(Integer.parseInt(pubYear.getText()) < 1800 || Integer.parseInt(pubYear.getText()) > 2024 || Integer.parseInt(pubYear.getText()) == 0) {
			displayErrorMessage("Please enter a year between 1800 and 2024");
			pubYear.requestFocus();
		}
		else if(status.getValue() == null) {
			displayErrorMessage("Please choose an option.");
			status.requestFocus();
		}
		else {
			insertBook();
			myModel.stateChangeRequest("LibraryOptions", null);
		}
	}

	// Call the insert book method
	private void insertBook() {
		Properties newBook = new Properties();
		newBook.setProperty("bookTitle", bookTitle.getText());
		newBook.setProperty("author", author.getText());
		newBook.setProperty("pubYear", pubYear.getText());

		Book book= new Book(newBook);
		book.update();
	}


	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		status.getItems().add("Active");
		status.getItems().add("Inactive");

		//bookId.setText((String)myModel.getState("bookId"));
//		bookTitle.setText((String)myModel.getState("bookTtitle"));
//		author.setText((String)myModel.getState("author"));
//	 	pubYear.setText((String)myModel.getState("pubYear"));
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		//if (key.equals("ServiceCharge") == true)
		//{
		//	String val = (String)value;
		//	pubYear.setText(val);
		//	displayMessage("Service Charge Imposed: $ " + val);
		//}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}

//---------------------------------------------------------------
//	Revision History:
//


