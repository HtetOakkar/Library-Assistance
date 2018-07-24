/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.editmember;

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
import javafx.stage.Stage;
import library.system.dao.MemberDAO;
import library.system.model.Member;
import library.system.util.Message;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class EditmemberController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;

    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
    }

    @FXML
    private void editMemberInfo(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            Message.showWarningMessage("Warning", "Please input all the required fields");
            return;
        }

        Member member = new Member(id, name, mobile, address);

        try {
            memberDAO.updateMembers(member);
            System.out.println("Updated!");
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setInitData(Member member) {
        idField.setDisable(true);
        idField.setText(Integer.toString(member.getId()));
        nameField.setText(member.getName());
        mobileField.setText(member.getMobile());
        addressField.setText(member.getAddress());
    }
}
