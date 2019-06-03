import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ConnectionDB {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSetForRead, resForLog, resForPass;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST6.s3db");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text);");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB(String log, String pass ) throws SQLException {
        statmt.execute("INSERT INTO 'users' ('login', 'password') VALUES ('" + log + "', '" + pass + "'); ");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws SQLException {
        JFrame frame = new JFrame("Table");
        frame.setDefaultCloseOperation(frame.HIDE_ON_CLOSE);
        resSetForRead = statmt.executeQuery("SELECT * FROM users");
        String[] columnNames = {
                "id",
                "Login",
                "Password",
        };
        String[][] data = new String[20][3];
        int i = 0;
        while(resSetForRead.next()) {
            String id = resSetForRead.getString("id");
            String  name = resSetForRead.getString("login");
            String  password = resSetForRead.getString("password");
            data [i][0] = id;
            data [i][1] = name;
            data [i][2] = password;
            i++;
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static boolean AuthLog (String login, String password) throws SQLException {
        resForLog = null;
        resForLog = statmt.executeQuery("SELECT * FROM users WHERE login = '" + login + "' AND password = '" + password + "'");
        if (resForLog.next()) {
            String str1 = resForLog.getString("login");
            String str2 = resForLog.getString("password");
            if (str1.equals(login) && str2.equals(password)) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public static String RegLog (String login) throws SQLException {
        resForPass = null;
        resForPass = statmt.executeQuery("SELECT login FROM users WHERE login = '" + login + "'");
        if (resForPass.next()) {
            String str3 = resForPass.getString("login");
            if (str3.equals(login)) {
                return "true";
            } else {
                return "false";
            }
        } else return "false";
    }

    public static void ChangePass (String login, String password) throws SQLException {
        statmt.execute("UPDATE users SET password = '" + password + "' WHERE login = '" + login + "'");
    }

    public static String CheckData(String log, String pass) throws Exception {
        String info;
        if (ConnectionDB.AuthLog(log, pass)) {
            info = "true";
        } else {
            info = "false";
        }
        return info;
    }

    // --------Закрытие--------
    public static void CloseDB() throws SQLException {
        conn.close();
        statmt.close();
    }
}