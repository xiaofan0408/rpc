package my.util;

import com.google.gson.Gson;

public class JsonUtil {

    private final static Gson gson = new Gson();

    public  static <T> T toObject(String objectStr, Class<T> clazz) {
        return gson.fromJson(objectStr, clazz);
    }

    public  static <T>  String toJson(T object) {
        return gson.toJson(object);
    }

}
