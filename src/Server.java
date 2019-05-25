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

                        boolean connect = true;

                        while (connect) {

                            String mode = in.readLine();
                            System.out.println(mode);
                            password = in.readLine();
                            System.out.println(password);
                            login = in.readLine();
                            System.out.println(login);
                            if (mode.equals("Auth")) {

                                String inform = ConnectionDB.CheckData(login, password);
                                System.out.println(inform);
                                System.out.println("46");
                                out.write(inform + "\n");
                                out.flush();

                            } else if (mode.equals("Reg")) {

                                String inform = ConnectionDB.CheckData(login, password);

                                if (inform.equals("true")) {
                                    System.out.println(inform);
                                    System.out.println("63");
                                    out.write(inform + "\n");
                                    out.flush();
                                } else if (inform.equals("false")) {
                                    ConnectionDB.WriteDB(login, password);
                                    System.out.println("72");
                                    out.write(inform + "\n");
                                    out.flush();
                                }
                            }

                            if ((password == null) && (login == null)) {
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

