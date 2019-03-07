package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class User {
    private String login;
    private String password;
    private String status;
    private boolean online;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[ Username: ").append(login)
                .append(", online: ").append(online).append(", status: ").append(status)
                .append("] ")
                .toString();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOnline() {
        return online;
    }

    public int setOnline(boolean online){
        try {
            URL obj = new URL(Utils.getURL() + "/set?status=" + online+"&login="+login);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            return conn.getResponseCode();
        }catch (IOException e){
            return  542;
        }

//        OutputStream os = conn.getOutputStream();
//        try {
////            Gson gson = new GsonBuilder().create();
////            System.out.println(login);
////            String json = gson.toJson(login);
////            System.out.println("Its json - "+json.getBytes());
////
////            os.write("".getBytes(StandardCharsets.UTF_8));
//
//       } finally {
////            os.close();
//       }
    }
}
