import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class ConnectionDB {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSetForRead, resForLog, resForPass;

    private static DataInputStream in;
    private static DataOutputStream out;
    private static Socket clientSocket;
    private static ServerSocket server;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:TEST6.s3db");

        System.out.println("База Подключена!");
    }
    // --------Создание таблицы--------
    public static void CreateDB() throws SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text);");
        System.out.println("Таблица создана или уже существует.");
    }
    // --------Заполнение таблицы--------
    public static void WriteDB(String log, String pass ) throws SQLException, IOException {
        //String strEn = pass;
        statmt.execute("INSERT INTO 'users' ('login', 'password') VALUES ('" + log + "', '" + pass + "'); ");
        System.out.println("Таблица заполнена");
    }
    // -------- Вывод таблицы--------
    public static void ReadDB() throws SQLException {
        resSetForRead = statmt.executeQuery("SELECT * FROM users");
        while(resSetForRead.next()) {
            int id = resSetForRead.getInt("id");
            String  name = resSetForRead.getString("login");
            String  password = resSetForRead.getString("password");
            System.out.println( "ID = " + id );
            System.out.println( "login = " + name );
            System.out.println( "password = " + password );
            System.out.println();
        } System.out.println("Таблица выведена");
    }
    // --------Проверка логина--------
    public static boolean AuthLog (String login, String password) throws SQLException {
        resForLog = null;
        resForLog = statmt.executeQuery("SELECT login FROM users WHERE password = '" + password + "'");
        if (resForLog.next()) {
            String str2 = resForLog.getString("login");
            if (str2.equals(login)) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }
    // --------Проверка пароля--------
    public static boolean AuthPass (String login, String password) throws SQLException {

        resForPass = null;
        resForPass = statmt.executeQuery("SELECT password FROM users WHERE login = '" + login + "'");
        if (resForPass.next()) {
            String str3 = resForPass.getString("password");
            String strEn = password;
            if (str3.equals(strEn)) {
                return true;
            } else {
                return false;
            }
        } else return false;
    }

    public static String CheckData(String log, String pass) throws Exception {
        String info;
        if ((ConnectionDB.AuthLog(log, pass)) && (ConnectionDB.AuthPass(log, pass))) {
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
        System.out.println("Соединения закрыты");
    }

    // --------Вычисление хеша--------

    /*
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Исключение: " + e);
        }
        return null;
    }*/
}