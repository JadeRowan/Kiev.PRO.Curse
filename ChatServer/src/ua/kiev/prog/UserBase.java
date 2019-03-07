package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedList;
import java.util.List;

public class UserBase {
    private static final UserBase uBase = new UserBase();

    private final Gson gson;
    private final List<User> list = new LinkedList<>();

    public static UserBase getInstance() {
        return uBase;
    }

    private UserBase() {
        gson = new GsonBuilder().create();
    }

    public synchronized void add(User user) {
        list.add(user);
    }

    public void setUserOnline(String login, boolean online){
        System.out.println("HOOOOO");
        for (User user: list) {
            if(user.getLogin().equals(login)){
                user.setOnline(online);
                break;
            }
        }
    }

    public synchronized String toJSON() {
        return gson.toJson(list);
    }
}
//Описать логику хранения и методы возврата пользователей
//В формате схожим с singleTone