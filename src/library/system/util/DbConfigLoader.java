/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.system.config.DbconfigController;
import library.system.model.DbConfig;

/**
 *
 * @author Htet-Oakkar
 */
public class DbConfigLoader {

    public static DbConfig loadDbConfig() {
        Properties prop = new Properties();
        DbConfig dbConfig = null;
        try (InputStream is = new FileInputStream("dbconfig.properties")) {

            prop.load(is);

            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            dbConfig = new DbConfig(host, Integer.parseInt(port), user, password);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbconfigController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbconfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConfig;
    }

    public static void saveDbConfig(DbConfig dbConfig) {
        Properties prop = new Properties();

        try (OutputStream os = new FileOutputStream("dbconfig.properties")) {
            prop.setProperty("host", dbConfig.getHost());
            prop.setProperty("port", Integer.toString(dbConfig.getPort()));
            prop.setProperty("user", dbConfig.getUser());
            prop.setProperty("password", dbConfig.getPassword());
            prop.store(os, "User Properties");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DbconfigController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DbconfigController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
