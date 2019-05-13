import java.io.*;
import java.net.Socket;

public class ClientFunc {

    private static Socket clientSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static String serverWord;
    private static String message;

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

    public static String TransferToSrvr(String passFromWind, String loginFromWind) {

                try {
                    clientSocket = new Socket("localhost", 4004);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    out.write(MD5(passFromWind) + "\n");
                    out.flush();
                    //String serverWord = in.readLine();
                    //System.out.println(serverWord);
                    out.write(loginFromWind + "\n");
                    out.flush();
                    //String serverWord1 = in.readLine();
                    //System.out.println(serverWord1);
                    serverWord = in.readLine();
                    //System.out.println(serverWord);
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

        System.out.println(message);

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