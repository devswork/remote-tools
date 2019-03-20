package com.github.devswork.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONUtil {
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static JSONObject parseJsonToJSONObject(String json) {
        return JSON.parseObject(json);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJsonToMap(String jsonString) {
        return parseJsonToObject(jsonString, Map.class);
    }


    public static <T> T parseJsonToObject(String jsonString, Class<T> c) {
        T result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, c);
        }
        return result;
    }


    public static <T> T parseJsonToObject(String jsonString, Class<T> c, ParseProcess process) {
        T result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, c, process);
        }
        return result;
    }

    public static <T> List<T> parseJsonToListBean(String jsonString) {
        List<T> result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, new TypeReference<List<T>>() {
            });
        }
        return result;
    }

    public static List<String> parseJsonToListString(String jsonString) {
        List<String> result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, new TypeReference<List<String>>() {
            });
        }
        return result;
    }

    public static List<Map<String, Object>> parseJsonToListMap(String jsonString) {
        List<Map<String, Object>> result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        }
        return result;
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    public static String[] parseJsonToArray(String jsonString) {
        String[] result = null;
        if (jsonString != null && jsonString.length() != 0) {
            result = JSON.parseObject(jsonString, new TypeReference<String[]>() {
            });
        }
        return result;
    }

    public static String toJSON(Object o) {
        return JSON.toJSONString(o, new OursValueFilter(null, null, true));
    }

    public static String toJSON(Object o, String dataFormat) {
        return JSON.toJSONString(o, new OursValueFilter(dataFormat, dataFormat, true));
    }

    public static String toJSON(Object o, String dateFormat, String dateTimeFormat) {
        return JSON.toJSONString(o, new OursValueFilter(dateFormat, dateTimeFormat, true));
    }

    public static String toJSON(Object o, String dataFormat, String dateTimeFormat, boolean isIncludeOrExclude, List<String> fields) {
        OursPropertyFilter filter1 = new OursPropertyFilter(o.getClass(), isIncludeOrExclude, fields);
        OursValueFilter filter2 = new OursValueFilter(dataFormat, dateTimeFormat, true);
        return JSON.toJSONString(o, new SerializeFilter[]{filter1, filter2});
    }

    public static String toJSON(Object o, boolean isIncludeOrExclude, List<String> fields) {
        OursPropertyFilter filter1 = new OursPropertyFilter(o.getClass(), isIncludeOrExclude, fields);
        return JSON.toJSONString(o, new SerializeFilter[]{filter1});
    }

    public static String toJSON(Object o, SerializeFilter[] filter) {
        return JSON.toJSONString(o, filter);
    }

    public static String toJSON(Object o, SerializeFilter[] filter, SerializerFeature[] features) {
        return JSON.toJSONString(o, filter, features);
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> c) {
        if (map == null || c == null) {
            return null;
        }
        T t = null;
        JSONObject obj = new JSONObject(map);
        t = JSON.toJavaObject(obj, c);
        return t;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object o) {
        Map<String, Object> map = ((Map<String, Object>) JSON4FastJSON.toJSON(o));
        return map;
    }

    public static String[] convert2StringArray(Object obj) {
        if (obj instanceof JSONArray) {
            JSONArray jArray = (JSONArray) obj;
            return convert2JavaArray(jArray, new String[jArray.size()]);
        }
        return null;
    }

    public static <T> T[] convert2JavaArray(Object obj, T[] t) {
        T[] ret = null;
        if (obj instanceof JSONArray) {
            JSONArray jArray = (JSONArray) obj;
            ret = jArray.toArray(t);
        }
        return ret;
    }
}


class OursPropertyFilter implements PropertyPreFilter {
    private final Class<?> clazz;
    private final Set<String> includes = new HashSet<String>();
    private final Set<String> excludes = new HashSet<String>();

    public OursPropertyFilter(boolean isIncludeOrExclude, List<String> properties) {
        this(null, isIncludeOrExclude, properties);
    }

    public OursPropertyFilter(Class<?> clazz, boolean isIncludeOrExclude, List<String> properties) {
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                if (isIncludeOrExclude) {
                    this.includes.add(item);
                } else {
                    this.excludes.add(item);
                }
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }
        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }
        if (this.excludes.contains(name)) {
            return false;
        }
        if (includes.size() == 0 || includes.contains(name)) {
            return true;
        }
        return false;
    }
}

class OursValueFilter implements ValueFilter {
    private boolean transLong2String = true;
    private String dateFormat = JSONUtil.FORMAT_DATE;
    private String dateTimeFormat = JSONUtil.FORMAT_DATETIME;

    public OursValueFilter(boolean transLong2String) {
        initParam(dateFormat, dateTimeFormat, transLong2String);
    }

    public OursValueFilter(String dateFormat, String dateTimeFormat, boolean transLong2String) {
        initParam(dateFormat, dateTimeFormat, transLong2String);
    }

    private void initParam(String dateFormat, String dateTimeFormat, boolean transLong2String) {
        if (dateFormat != null) {
            this.dateFormat = dateFormat;
        }
        if (dateTimeFormat != null) {
            this.dateTimeFormat = dateTimeFormat;
        }
        this.transLong2String = transLong2String;
    }

    public Object process(Object object, String name, Object value) {
        if (value instanceof java.sql.Timestamp) {
            java.sql.Timestamp d = (java.sql.Timestamp) value;
            value = DateUtil.getDate(d, dateTimeFormat);
        } else if (value instanceof java.util.Date) {
            java.util.Date d = (java.util.Date) value;
            value = DateUtil.getDate(d, dateFormat);
        } else if (value instanceof Long) {
            if (transLong2String) {
                value = value.toString();
            }
        }
        return value;
    }
}
