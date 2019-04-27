import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class DataBase {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        ConnectionDB.Conn();
        ConnectionDB.CreateDB();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowAUTH();
            }
        });
    }
}