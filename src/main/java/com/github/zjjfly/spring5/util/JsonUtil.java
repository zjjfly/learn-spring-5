package com.github.zjjfly.spring5.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Json util class
 *
 * @author zjjfly[https://github.com/zjjfly]
 * @date 2021-02-02
 */
@Slf4j
public class JsonUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtil() {
        throw new IllegalStateException("JsonUtil utility class");
    }

    public static <T> T readValue(String json, Class<T> clazz)
            throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    public static Map<String, Object> toMap(Object o) {
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        String json = JSONObject.toJSONString(o);
        return JSONObject.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Map<String, Object>> toMapList(List<?> objs) {
        return objs.stream().map(JsonUtil::toMap).collect(Collectors.toList());
    }
}
