package top.zopx.goku.framework.tools.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 默认Json方法
 *
 * @author Mr.Xie
 * @email xiezhyan@126.com
 * @date 2022/2/11
 */
public final class GsonUtil{

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private final Gson gson;

    private GsonUtil() {
        gson = buildGson();
    }

    private static class Holder {
        public static final GsonUtil INSTANCE = new GsonUtil();
    }

    public static GsonUtil getInstance() {
        return Holder.INSTANCE;
    }

    public Gson getGson() {
        return gson;
    }

    public <T> String toJson(T obj) {
        return gson.toJson(obj);
    }

    public <T> T toObject(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <T> T toObject(Reader json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <T> List<T> toList(String json, Class<T[]> clazz) {
        T[] ts = gson.fromJson(json, clazz);
        return Arrays.asList(ts);
    }

    public <K, V> Map<K, V> toMap(String json) {
        Type type = new TypeToken<Map<K, V>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private Gson buildGson() {
        return new GsonBuilder()
                // 序列化
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)))
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(YYYY_MM_DD)))
                )
                .registerTypeAdapter(
                        Date.class,
                        (JsonSerializer<Date>) (src, type, context) ->
                                new JsonPrimitive(src.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS))
                                )
                )
                // 反序列化
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (jsonElement, type, context) ->
                                LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS))
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonDeserializer<LocalDate>) (jsonElement, type, context) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(YYYY_MM_DD))
                )
                .registerTypeAdapter(
                        Date.class,
                        (JsonDeserializer<Date>) (jsonElement, type, context) ->
                                Date.from(
                                        LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS)).atZone(ZoneId.systemDefault()).toInstant()
                                )
                )
                .serializeNulls()
                .create();
    }
}
