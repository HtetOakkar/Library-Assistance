/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.system.dao.BookDAO;
import library.system.model.Book;
import library.system.util.Message;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class AddbookController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton saveBtn;
    
    private BookDAO bookDAO ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       bookDAO = new BookDAO();
        
    }

    @FXML
    private void saveBookInfo(ActionEvent event) {

        int id = 0;

        try {
            String idstr = idField.getText();
            if (!idstr.isEmpty()) {
                id = Integer.parseInt(idstr);
            }
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid input for the id field!");
            return;
        }

        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            Message.showWarningMessage("Warning", "Please fill all the required fields!");
            return;
        }

        Book book = new Book(id, title, author, publisher);
        
        try {
            bookDAO.saveBook(book);
            System.out.println("Success!");
        } catch (SQLException ex) {
            Message.showErrorMessage("Error", "Faild!");
            Logger.getLogger(AddbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        idField.clear();
        titleField.clear();
        authorField.clear();
        publisherField.clear();
    }

}
