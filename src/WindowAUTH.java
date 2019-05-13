import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class WindowAUTH extends JFrame {

    private JPasswordField passAuthField = new JPasswordField(15);
    private JTextField loginAuthField = new JTextField( 15);
    private JPasswordField passRegField = new JPasswordField(15);
    private JTextField loginRegField = new JTextField( 15);

    private static String message, passFromWind, loginFromWind;

    /*private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String serverWord;*/

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
    }

    public WindowAUTH() {

        //ClientFunc.ConnectToSrvr(); //Connection to server

        JFrame MainWindowAuth = new JFrame("Аутентификация");
        MainWindowAuth.setLocationRelativeTo(null);
        MainWindowAuth.setSize(350, 150);
        MainWindowAuth.setVisible(true);
        MainWindowAuth.setResizable(false);
        MainWindowAuth.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               ClientFunc.ConnectionClose();
               System.exit(0);
            }
        });
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(4,2));
        contents.add(new JLabel("Логин/Login:"));
        contents.add(loginAuthField);
        contents.add(new JLabel("Пароль/Password:"));
        contents.add(passAuthField);
        passAuthField.setEchoChar('*');
        JButton enterButton = new JButton("Ввод");
        contents.add(enterButton);
        JButton cancelButton = new JButton("Отмена");
        contents.add(cancelButton);
        JButton registration = new JButton("Регистрация");
        contents.add(registration);
        JRadioButton rb1 = new JRadioButton("Показать пароль", false);
        contents.add(rb1);
        MainWindowAuth.add(contents);
        rb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rb1.isSelected()) {
                    passAuthField.setEchoChar((char)0);
                } else {
                    passAuthField.setEchoChar('*');
                }
            }
        });

        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                        passFromWind = passAuthField.getText();
                        loginFromWind = loginAuthField.getText();
                Thread thrd = new Thread(){
                    public void run(){
                        System.out.println("aaaa");
                        message = ClientFunc.TransferToSrvr(loginFromWind, passFromWind);
                        }
                    };
                thrd.run();
                //while (thrd.isAlive()) {
                    //thrd.start();
                    //String message = ClientFunc.TransferToSrvr(loginFromWind, passFromWind);
                    System.out.println("zzzz");
                    System.out.println(message);
                    InfoWind("Аутентификация", message);
                    System.out.println("gtrgbd");
                    //thrd.stop();
                //}

            }
        });

        registration.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegWind ("Регистрация");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ConnectionDB.CloseDB();
                } catch (SQLException e1) {
                    System.out.println("Проблема с закрытием БД: " + e1);
                }
                System.exit(0);
            }
        });
    }

    // --------Окно регистрации--------
    public void RegWind (String name) {
        JFrame RegWindFrame = new JFrame(name);
        RegWindFrame.setVisible(true);
        RegWindFrame.setSize(350, 120);
        RegWindFrame.setLocationRelativeTo(null);
        RegWindFrame.setResizable(false);
        RegWindFrame.setLayout(new GridLayout(3,1));
        JLabel LoginLabel = new JLabel("Логин/login:");
        LoginLabel.setHorizontalAlignment(SwingConstants.LEFT);
        RegWindFrame.add(LoginLabel);
        RegWindFrame.add(loginRegField);
        JLabel PassLabel = new JLabel("Пароль/Password:");
        PassLabel.setHorizontalAlignment(SwingConstants.LEFT);
        RegWindFrame.add(PassLabel);
        RegWindFrame.add(passRegField);
        passRegField.setEchoChar('*');
        JRadioButton rb2 = new JRadioButton("Показать пароль", false);
        RegWindFrame.add(rb2);
        JButton input = new JButton("Ввод");
        RegWindFrame.add(input);
        rb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rb2.isSelected()) {
                    passRegField.setEchoChar((char)0);
                } else {
                    passRegField.setEchoChar('*');
                }
            }
        });

        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)  {
                String passFromWind = passRegField.getText();
                String loginFromWind = loginRegField.getText();
                if ((loginRegField.getText().trim().length() <= 0) || (passRegField.getText().trim().length() <= 0) ) {
                    InfoWind ("Ошибка ввода", "Поля не должны быть пустыми");
                } else {
                    try {
                        ConnectionDB.WriteDB(loginFromWind, passFromWind);
                    } catch (Exception ex) {
                        System.out.println("Ошибка записи " + ex);
                    }
                }
            }
        });
    }

    public void InfoWind (String name, String action) {
        JFrame message = new JFrame(name);
        message.setVisible(true);
        message.setSize(350, 110);
        message.setLocationRelativeTo(null);
        message.setResizable(false);
        message.setLayout(new GridLayout(2,1));
        JPanel pannel = new JPanel();
        message.add(pannel);
        JLabel myLabel = new JLabel(action);
        myLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabel.setVerticalAlignment(SwingConstants.CENTER);
        pannel.add(myLabel);
        JPanel pannel1 = new JPanel();
        message.add(pannel1);
        JButton ok = new JButton("Ok");
        pannel1.add(ok);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                message.setVisible(false);
            }
        });
    }
}