/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.config;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.system.model.DbConfig;
import library.system.util.DbConfigLoader;

/**
 * FXML Controller class
 *
 * @author Htet-Oakkar
 */
public class DbconfigController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DbConfig dbConfig = DbConfigLoader.loadDbConfig();
        if (dbConfig == null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, 3306);
            portSpinner.setValueFactory(valueFactory);
        } else {
            String host = dbConfig.getHost();
            int port = dbConfig.getPort();
            String user = dbConfig.getUser();
            String password = dbConfig.getPassword();
            hostField.setText(host);
            usernameField.setText(user);
            passwordField.setText(password);

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, port);
            portSpinner.setValueFactory(valueFactory);
        }
    }

    @FXML
    private void saveDatabaseConfig(ActionEvent event) {

        String host = hostField.getText();
        int port = portSpinner.getValue();
        String user = usernameField.getText();
        String password = passwordField.getText();

        DbConfig dbConfig = new DbConfig(host, port, user, password);

        DbConfigLoader.saveDbConfig(dbConfig);

        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

}
