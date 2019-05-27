import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class WindowServer {

    public WindowServer () {

        JFrame MainWindowAuth = new JFrame("Сервер");
        MainWindowAuth.setLocationRelativeTo(null);
        MainWindowAuth.setSize(350, 150);
        MainWindowAuth.setVisible(true);
        MainWindowAuth.setResizable(false);
        MainWindowAuth.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    ConnectionDB.CloseDB();
                } catch (SQLException ex) {
                    System.out.println("Проблема с закрытием БД" + ex);
                    System.exit(0);
                }
                System.exit(0);
            }
        });
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(3,1));
        Font font = new Font("Century Gothic", Font.BOLD, 14);
        JLabel label = new JLabel("Сервер запущен, база подключена");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        contents.add(label);
        JButton tableButton = new JButton("Показать таблицу");
        tableButton.setFont(font);
        contents.add(tableButton);
        JButton closeButton = new JButton("Выключить сервер");
        closeButton.setFont(font);
        contents.add(closeButton);
        MainWindowAuth.add(contents);

        tableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ConnectionDB.ReadDB();
                } catch (SQLException one) {
                    System.out.println(one);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ConnectionDB.CloseDB();
                } catch (SQLException ex) {
                    System.out.println("Проблема с закрытием БД" + ex);
                    System.exit(0);
                }
                System.exit(0);
            }
        });
    }
}