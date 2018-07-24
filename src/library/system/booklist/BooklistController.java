/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.booklist;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.editbook.EditbookController;
import library.system.model.Book;
import library.system.util.Message;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class BooklistController implements Initializable {

    private BookDAO bookDAO;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titlecolumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
        initColumn();
        loadTableData();
    }

    private void initColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

    }

    private void loadTableData() {

        try {
            ObservableList<Book> list = bookDAO.getBooks();
            bookTable.getItems().setAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteBook(ActionEvent event) {

        Book selectedbook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedbook == null) {
            Message.showWarningMessage("Warning", "Please select book you want to delete!");
            return;
        }
        try {
            bookDAO.deleteBook(selectedbook.getId());
            bookTable.getItems().remove(selectedbook);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadEditView(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/system/editbook/editbook.fxml"));
        Parent root = loader.load();

        Book selectedbook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedbook == null) {
            Message.showWarningMessage("Warning", "Please select book you want to edit!");
            return;
        }

        EditbookController controller = loader.getController();
        controller.setInitData(selectedbook);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(bookTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Edit");
        stage.showAndWait();
        loadTableData();
    }
}
