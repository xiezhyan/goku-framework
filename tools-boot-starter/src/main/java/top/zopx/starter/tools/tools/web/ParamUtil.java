package top.zopx.starter.tools.tools.web;

import com.alibaba.fastjson.JSON;
import top.zopx.starter.tools.tools.strings.DigestUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * version: 参数操作
 * ---------------------
 * @author sanq.Yan
 * @date 2020/1/26
 */
public class ParamUtil {
    private static final WeakHashMap<String, String> PARAMS = new WeakHashMap<>();
    public static final String KEY = "params";
    private ParamUtil() {
    }

    public static ParamUtil getInstance() {
        return ParamUtil.Holder.INSTANCE;
    }

    static class Holder {
        private static final ParamUtil INSTANCE = new ParamUtil();
    }

    /**
     * 将从post中获取到的传递 存放到map中
     */
    public void set(String params) {
        PARAMS.put(KEY, params);
    }

    /**
     * 得到json字符串
     */
    public String get() {
        return PARAMS.get(KEY);
    }

    /**
     * 将json字符串转换成map对象
     */
    public Map<String, Object> json2Map(String json) {
        return (Map<String, Object>) JSON.parse(json);
    }

    /**
     * 签名验证
     */
    public String getSign(Map<String, Object> map) throws Exception {
        return getSign2Map(map);
    }

    private String getSign2Map(Map<String, Object> map) throws Exception {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> list = new ArrayList<String>(map.keySet());
        Collections.sort(list);

        for(String key:list) {
            Object value = map.get(key);
            if(!key.equalsIgnoreCase("sign"))
                sb.append(key).append("=").append(value).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return DigestUtil.getInstance().md5(sb.toString());
    }

    public String getSign(String json) throws Exception {
        Map<String, Object> map = json2Map(json);
        return getSign2Map(map);
    }

    /**
     * 从get请求中获取到参数 封装成Map对象
     */
    public Map<String, Object> getParam2Get(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();

        if(parameterMap != null && !parameterMap.isEmpty()) {
            for(Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String[] value = entry.getValue();
                if(value != null && value.length > 0) {
                    map.put(entry.getKey(), value[0]);
                }
            }
        }

        return map;
    }
}
