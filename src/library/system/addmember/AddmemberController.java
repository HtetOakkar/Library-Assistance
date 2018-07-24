/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.addmember;

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
import library.system.dao.MemberDAO;
import library.system.model.Member;
import library.system.util.Message;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class AddmemberController implements Initializable {

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

    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
    }

    @FXML
    private void saveMemberInfo(ActionEvent event) {
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
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            System.out.println("Please fill in all the required fields!");
            return;
        }

        Member member = new Member(id, name, mobile, address);

        try {
            memberDAO.saveMember(member);
            System.out.println("Success!");
        } catch (SQLException ex) {
            Message.showWarningMessage("Warning", "Please fill all the required fields!");
            Logger.getLogger(AddmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
        idField.clear();
        nameField.clear();
        mobileField.clear();
        addressField.clear();
    }

}
