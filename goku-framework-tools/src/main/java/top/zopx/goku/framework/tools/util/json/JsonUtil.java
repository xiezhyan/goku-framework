package top.zopx.goku.framework.tools.util.json;

import com.google.gson.*;
import top.zopx.goku.framework.tools.util.json.impl.GJson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 默认Json方法
 *
 * @author 俗世游子
 * @email xiezhyan@126.com
 * @date 2022/2/11
 */
public final class JsonUtil implements IJson {

    private final IJson json;

    private final Gson gson;

    private JsonUtil() {
        gson = getDefaultGson();
        json = new GJson(getDefaultGson());
    }

    @Override
    public <T> String toJson(T obj) {
        return json.toJson(obj);
    }

    @Override
    public <T> T toObject(String jsonText, Class<T> clazz) {
        return json.toObject(jsonText, clazz);
    }

    @Override
    public <T> List<T> toObjList(String jsonText, Class<T[]> clazz) {
        return json.toObjList(jsonText, clazz);
    }

    @Override
    public <K, V> Map<K, V> toMap(String jsonText) {
        return json.toMap(jsonText);
    }

    private static class Holder {
        public static final JsonUtil INSTANCE = new JsonUtil();
    }

    public static JsonUtil getInstance() {
        return Holder.INSTANCE;
    }

    public Gson getGson() {
        return gson;
    }

    @Deprecated
    public IJson getJson() {
        return json;
    }

    private Gson getDefaultGson() {
        return new GsonBuilder()
                // 序列化
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonSerializer<LocalDate>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                )
                .registerTypeAdapter(
                        Date.class,
                        (JsonSerializer<Date>) (src, type, context) ->
                                new JsonPrimitive(src.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                )
                )
                // 反序列化
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, context) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
                .registerTypeAdapter(
                        LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, context) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )
                .registerTypeAdapter(
                        Date.class,
                        (JsonDeserializer<Date>) (json, type, context) ->
                                Date.from(
                                        LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant()
                                )
                )
                .serializeNulls()
                .create();
    }
}
