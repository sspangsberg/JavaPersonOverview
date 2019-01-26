package ch.makery.address.view;

//Java imports
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Project imports
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**
 * FXML Controller class
 *
 * @author spangsberg
 */
public class PersonEditDialogController implements Initializable {

    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField streetField;
    
    @FXML
    private TextField cityField;
    
    @FXML
    private TextField postalCodeField;
    
    @FXML
    private TextField birthdayField;
    
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    /**
     * Sets the stage of the dialog.
     *  
     * @param dialogStage 
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    /**
     * Sets the Person object in the dialog to be edited
     * 
     * @param person 
     */
    public void setPerson(Person person) {
        this.person = person;
        
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        cityField.setText(person.getCity());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        
        birthdayField.setPromptText("dd.mm.yyyy");
    }   
    
    
    /**
     * Returns true if the user clicked OK, false otherwise
     * 
     * @return 
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    
    /**
     * Called when the user clicks OK
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setCity(cityField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setStreet(streetField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            
            okClicked = true;
            dialogStage.close();            
        }
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return 
     */
    @FXML
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }
        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        }
        //try to parse the postal code
        else {
            try {
                Integer.parseInt(postalCodeField.getText());
            }
            catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }
        
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }
      
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        }
        else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use dd.mm.yyyy";
            }
        }
        
        if (errorMessage.length() == 0)
        {
            return true;
        }
        else
        {
            //Show the alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields.");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
    
    
    
    
    /**
     * Called when the user clicks cancel
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
    
    
}
