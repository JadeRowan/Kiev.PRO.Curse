package ua.kiev.prog.oauth2.loginviagoogle.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccountDTO {
    private final String email;
    private final String name;
    private final String pictureUrl;

    private AccountDTO(String email, String name, String pictureUrl) {
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public static AccountDTO of(String email, String name, String pictureUrl) {
        return new AccountDTO(email, name, pictureUrl);
    }

    //Я действительно не знаю корректно ли вставлять сюда данную логику и делать клас статическим.
    //Очень надеюсь что вы расскажите как именно стоило это сделать
    public static String getPictureFrom(Map <String, Object> attributes){
        if (attributes.containsKey("picture")){
            if (attributes.get("picture").getClass().equals(String.class)){
                return (String) attributes.get("picture");
            }
            if (attributes.get("picture").getClass().equals(LinkedHashMap.class)){
                //Мне показалось что эти проверки уместны, во избежания NullPointerExeption
                Map<String,Object> crazyFacebookPictureDataMap = (Map<String, Object>) attributes.get("picture");
                if(crazyFacebookPictureDataMap.containsKey("data")) {
                    Map<String, Object>  dataObj = (Map<String, Object>) crazyFacebookPictureDataMap.get("data");
                    if(dataObj.containsKey("url")) {
                        return (String) dataObj.get("url");
                    }
                }
            }

        }
        if (attributes.containsKey("avatar_url")){
            return (String) attributes.get("avatar_url");
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
