/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.issuedbooklist;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.system.booklist.BooklistController;
import library.system.dao.IssueDAO;
import library.system.model.IssueInfo;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class IssuedBooklistController implements Initializable {

    @FXML
    private TableColumn<IssueInfo, Integer> bookIdColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> memberIdColumn;
    @FXML
    private TableColumn<IssueInfo, Date> issuedDateColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> renewCountColumn;
    @FXML
    private TableView<IssueInfo> issuedBookistTable;

    private IssueDAO issueDAO;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        issueDAO = new IssueDAO();
        initColumn();
        loadTableData();
    }

    private void initColumn() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        renewCountColumn.setCellValueFactory(new PropertyValueFactory<>("renewCount"));
    }

    private void loadTableData() {
        try {
            ObservableList<IssueInfo> list = issueDAO.getIssuedInfos();
            issuedBookistTable.getItems().setAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
