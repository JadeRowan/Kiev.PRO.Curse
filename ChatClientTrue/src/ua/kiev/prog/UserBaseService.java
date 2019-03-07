package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserBaseService {
    private static final UserBaseService USB = new UserBaseService();
    private final Gson gson;

    public UserBaseService() {
        gson = new GsonBuilder().create();
    }
    public User[] getUsers(){
        User[] array = null;
        try {
                URL url = new URL(Utils.getURL() + "/getusers");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();
                try {
                    byte[] buf = GetThread.requestBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);

                     array = gson.fromJson(strBuf, User[].class);

                } finally {
                    is.close();
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return array;
    }

    public int addUser(String login, String password) throws IOException {
        User user = new User(login, password);
        URL obj = new URL(Utils.getURL() + "/adduser");
        HttpURLConnection conn = (HttpURLConnection)
                obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        try {
            String json = user.toJSON(); /* добавил .user*/
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode();
        } finally {
            os.close();
        }

        //Описать логику составления запроса и отправки по открытому соединению
        //Для добавления нового пользователяы

    }
    public static boolean containsUser(String login){
        User[] userBase = USB.getUsers();
        if(userBase == null){return false;}
        for (User u: userBase) {
            if(u != null & u.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    public static void printUsers(){
        User[] users = USB.getUsers();
        for (User u: users) {
            System.out.println(u);
        }
    }
}
