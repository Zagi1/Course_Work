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
        //MainWindowAuth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainWindowAuth.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ConnectionDB.ConnectionClose();
                System.exit(0);
            }
        });

        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(4,2));
        contents.add(new JLabel("Сервер запущен, база подключена"));
        JButton tableButton = new JButton("Показать таблицу");
        contents.add(tableButton);
        JButton closeButton = new JButton("Выключить сервер");
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
                ConnectionDB.ConnectionClose();
                System.exit(0);
            }
        });

    }
}