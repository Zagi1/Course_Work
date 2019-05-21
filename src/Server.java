import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String password;
    private static String login;

    //private static DataInputStream in;
    //private static DataOutputStream out;

    public static void main(String[] args) {
        try {

            ConnectionDB.Conn();
            ConnectionDB.CreateDB();

            try {
                server = new ServerSocket(4004);
                System.out.println("Сервер запущен!");
                boolean waitConnect = true;
                while (waitConnect) {
                    clientSocket = server.accept();
                    try {
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                        //in = new DataInputStream(clientSocket.getInputStream());
                        //out = new DataOutputStream(clientSocket.getOutputStream());

                        boolean connect = true;

                        while (connect) {

                            password = in.readLine();
                            System.out.println(password);
                            login = in.readLine();
                            System.out.println(login);
                            String inform = ConnectionDB.CheckData(login, password);
                            System.out.println(inform);
                            System.out.println("54");
                            out.write(inform + "\n");
                            out.flush();

                            if ((password == null)&&(login ==null)) {
                                connect = false;
                                clientSocket = null;
                            }
                        }

                        //out.close();
                        //ConnectionDB.DataToClient();

                        //if (login.equalsIgnoreCase("exit")) break;


                        //in.close();
                        //out.close();
                        //clientSocket.close();

                    } catch (Exception e) {
                        System.err.println(e);
                    }
                /*finally {
                    System.out.println("dsada");
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }*/

                }

            } catch (Exception e) {
                System.err.println(e);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

