import java.io.*;
import java.net.Socket;

public class ClientFunc {

    public static Socket clientSocket;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static String serverWord;
    public static String message;

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

    /*
    public static void ConnectToSrvr() {
        try {
            clientSocket = new Socket("localhost", 4004);
            in = new DataInputStream(clientSocket.getInputStream());
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }*/

    public static String TransferToSrvr(String login, String password) {

        Thread thrd = new Thread(){

            private volatile String login;

            public void setData(String login){
                this.login = login;
            }

            //private String login, password;
            public void run(){

                try {
                    clientSocket = new Socket("localhost", 4004);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    out.write(MD5(password) + "\n");
                    out.flush();
                    //String serverWord = in.readLine();
                    //System.out.println(serverWord);
                    out.write(login + "\n");
                    out.flush();
                    serverWord = in.readLine();
                    System.out.println(serverWord);
                    //in.close();

                    if (serverWord.equals("true")) {
                        message = "Аутентификация пройдена успешно!";
                    } else {
                        message = "Ошибка! Проверьте введенные данные!";
                    }

                    System.out.println(message);

                    clientSocket.close();

                } catch (
                        Exception ex) {
                    System.out.println("Исключение: " + ex);
                }
            }
        };
        thrd.start();
        System.out.println("TransferToSrvr func");
        System.out.println(message);
        thrd.stop();

        return message;

    }

    public static void ConnectionClose() {
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (Exception e1) {
            System.out.println(e1);
        }
    }

}