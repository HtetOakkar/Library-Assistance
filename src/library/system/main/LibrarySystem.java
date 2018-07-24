/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.system.database.Database;
import library.system.util.Message;

/**
 *
 * @author Htet-Oakkar
 */
public class LibrarySystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        try {
            Database db = Database.getInstance();
        } catch (SQLException e) {
            Message.showAndWaitErrorMessage("Connection Error", "Can't connect to database!");
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
