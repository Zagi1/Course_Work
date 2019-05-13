import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class Client {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowAUTH();
            }
        });
    }
}