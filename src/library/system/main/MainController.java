/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.dao.IssueDAO;
import library.system.dao.MemberDAO;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;
import library.system.util.Message;

/**
 *
 * @author Htet-Oakkar
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton bookListBtn;
    @FXML
    private JFXButton memberBtn;
    @FXML
    private JFXButton memberlistBtn;
    @FXML
    private JFXTextField searchBookIdField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;
    @FXML
    private Text availableText;
    @FXML
    private JFXTextField searchMemberIdField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;

    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueDAO issueDAO;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField searchBookIdInfo;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text nNameText;
    @FXML
    private Text nMobileText;
    @FXML
    private Text nAddressText;
    @FXML
    private Text issuedDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton returnBtn;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton issuedBooklistBtn;
    @FXML
    private MenuItem dbConfigItem;

    private final String defaultStyle = "-fx-border-width:0 0 0 5px; -fx-border-color:#123456";
    private final String activeStyle = "-fx-border-width:0 0 0 5px; -fx-border-color:#eceff1";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeActive();
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueDAO = new IssueDAO();

    }

    @FXML
    private void loadHomeView(ActionEvent event) {
        homeActive();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);
    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        bookActive();
        loadView("/library/system/addbook/addbook.fxml");

    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        bookListActive();
        loadView("/library/system/booklist/booklist.fxml");

    }

    @FXML
    private void loadAddMemberView(ActionEvent event) throws IOException {
        memberActive();
        loadView("/library/system/addmember/addmember.fxml");

    }

    @FXML
    private void loadMemberListView(ActionEvent event) throws IOException {
        memberListActive();
        loadView("/library/system/memberlist/memberlist.fxml");

    }

    @FXML
    private void issuedBooklistView(ActionEvent event) throws IOException {
        issueActive();
        loadView("/library/system/issuedbooklist/IssuedBooklist.fxml");
    }

    @FXML
    private void searchBookInfo(ActionEvent event) {

        clearbookCache();

        String idStr = searchBookIdField.getText();

        if (idStr.isEmpty()) {
            Message.showWarningMessage("Warining", "Please fill the book id first!");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Book book = bookDAO.getBook(id);

            if (book != null) {
                String title = book.getTitle();
                String author = book.getAuthor();
                String publisher = book.getPublisher();
                boolean available = book.isAvailable();
                titleText.setText(title);
                authorText.setText(author);
                publisherText.setText(publisher);

                if (available) {
                    availableText.setText("Available");
                } else {
                    availableText.setText("Not Available");
                }
            } else {
                Message.showErrorMessage("Error", "Cannot find book for this id!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
        }
    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {

        clearMemberCache();

        String idStr = searchMemberIdField.getText();
        if (idStr.isEmpty()) {
            Message.showWarningMessage("Warning ", "Please fill in id first!");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Member member = memberDAO.getmember(id);

            if (member != null) {
                nameText.setText(member.getName());
                mobileText.setText(member.getMobile());
                addressText.setText(member.getAddress());
            } else {
                Message.showErrorMessage("Error", "Cannot find member for this id!");
            }
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearbookCache() {
        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");
    }

    private void clearMemberCache() {
        nameText.setText("-");
        mobileText.setText("-");
        addressText.setText("-");
    }

    @FXML
    private void issueBook(ActionEvent event) {
        String bookIdStr = searchBookIdField.getText();
        String memberIdStr = searchMemberIdField.getText();
        if (bookIdStr.isEmpty() || memberIdStr.isEmpty()) {
            Message.showWarningMessage("Warning", "Please fill all the require field first!");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdStr);
            int memberId = Integer.parseInt(memberIdStr);
            IssueInfo issue = new IssueInfo(bookId, memberId);
            Book book = bookDAO.getBook(bookId);

            if (book.isAvailable()) {
                issueDAO.saveIssueInfo(issue);
                bookDAO.updateAvailable(bookId, false);
                System.out.println("success!");
            } else {
                Message.showErrorMessage("Error", "This book id is already issued!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
            return;
        }
    }

    @FXML
    private void searchIssuedBooks(ActionEvent event) {
        String bookIdStr = searchBookIdInfo.getText();

        if (bookIdStr.isEmpty()) {
            Message.showWarningMessage("Warning", "Please fill in id first!");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssuedInfo(bookId);

            if (issueInfo != null) {
                Book book = bookDAO.getBook(issueInfo.getBookId());
                Member member = memberDAO.getmember(issueInfo.getMemberId());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());

                nNameText.setText(member.getName());
                nMobileText.setText(member.getMobile());
                nAddressText.setText(member.getAddress());

                issuedDateText.setText("Issued Date : " + issueInfo.getIssueDate());
                renewCountText.setText("Renew Count : " + issueInfo.getRenewCount());
            } else {
                Message.showErrorMessage("Error", "This book is not issued!");
                clearRetrunCache();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void returnBook(ActionEvent event) {
        String bookIdStr = searchBookIdInfo.getText();

        if (bookIdStr.isEmpty()) {
            Message.showWarningMessage("Warning", "Please fill in id first");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssuedInfo(bookId);
            if (issueInfo != null) {

                Optional<ButtonType> option = Message.showConfirmMessage("Comfirmation", "Return book?");
                if (option.get() == ButtonType.OK) {
                    issueDAO.deleteIssueInfo(bookId);
                    bookDAO.updateAvailable(bookId, true);
                }
            } else {
                Message.showErrorMessage("Error", "Cannot find book for this id!");

            }
            clearRetrunCache();
            searchBookIdInfo.clear();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void renewBook(ActionEvent event) {
        String bookIdStr = searchBookIdInfo.getText();

        if (bookIdStr.isEmpty()) {
            Message.showWarningMessage("Warning", "Please fill in id first");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            Message.showErrorMessage("Error", "Invalid Input!");
            return;
        }
        IssueInfo issueInfo;
        try {
            issueInfo = issueDAO.getIssuedInfo(bookId);
            if (issueInfo != null) {
                issueDAO.updateIssueInfo(bookId);
            } else {
                Message.showErrorMessage("Error", "This book is not issued!");
            }
            searchBookIdInfo.clear();
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearRetrunCache() {
        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");

        nNameText.setText("-");
        nMobileText.setText("-");
        nAddressText.setText("-");

        issuedDateText.setText("-");
        renewCountText.setText("-");
    }

    private void loadView(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadDbConfigView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/system/config/dbconfig.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initOwner(centerPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void homeActive() {
        homeBtn.setStyle(activeStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberlistBtn.setStyle(defaultStyle);
        issuedBooklistBtn.setStyle(defaultStyle);
    }

    private void bookActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(activeStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberlistBtn.setStyle(defaultStyle);
        issuedBooklistBtn.setStyle(defaultStyle);
    }

    private void bookListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(activeStyle);
        memberBtn.setStyle(defaultStyle);
        memberlistBtn.setStyle(defaultStyle);
        issuedBooklistBtn.setStyle(defaultStyle);
    }

    private void memberActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(activeStyle);
        memberlistBtn.setStyle(defaultStyle);
        issuedBooklistBtn.setStyle(defaultStyle);
    }

    private void memberListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberlistBtn.setStyle(activeStyle);
        issuedBooklistBtn.setStyle(defaultStyle);
    }

    private void issueActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberlistBtn.setStyle(defaultStyle);
        issuedBooklistBtn.setStyle(activeStyle);
    }

}
