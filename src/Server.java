import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String password, oldpassword;
    private static String login;

    public static void main(String[] args) {

        try {
            ConnectionDB.Conn();
            ConnectionDB.CreateDB();
            server = new ServerSocket(4004);
            boolean waitConnect = true;
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                        new WindowServer();
                    }
            });
            while (waitConnect) {
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    boolean connect = true;
                    while (connect) {
                            String mode = in.readLine();
                            password = in.readLine();
                            if (mode.equals("Change")) {
                                oldpassword = in.readLine();
                            }
                            login = in.readLine();
                            if (mode.equals("Auth")) {
                                String inform = ConnectionDB.CheckData(login, password);
                                out.write(inform + "\n");
                                out.flush();
                            } else if (mode.equals("Reg")) {
                                String inform = ConnectionDB.RegLog(login);
                                if (inform.equals("true")) {
                                    out.write(inform + "\n");
                                    out.flush();
                                } else if (inform.equals("false")) {
                                    ConnectionDB.WriteDB(login, password);
                                    out.write(inform + "\n");
                                    out.flush();
                                }
                            } else if (mode.equals("Change")){
                                String informChange = ConnectionDB.CheckData(login, oldpassword);
                                if (informChange.equals("true")) {
                                    ConnectionDB.ChangePass(login, password);
                                    out.write(informChange + "\n");
                                    out.flush();
                                } else if (informChange.equals("false")) {
                                    out.write(informChange + "\n");
                                    out.flush();
                                }
                            }
                            if ((password == null) && (login == null)) {
                                connect = false;
                                clientSocket.close();
                            }
                        }
                    } catch (Exception q) {
                        System.err.println(q);
                    }
                    clientSocket.close();
                    out.close();
                    in.close();
                }
        } catch (Exception e1) {
            System.err.println(e1);
        }
    }
}


