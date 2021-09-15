package com.zy.timetoolsgp.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences管理类
 */
public class SharedPreferencesUtil {
    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;

    /**
     * 初始化方法
     *
     * @param context
     * @param prefs_name 名称
     * @param mode       模式
     */
    public static void newInstance(Context context, String prefs_name, int mode) {
        sSharedPreferences = context.getSharedPreferences(prefs_name, mode);
        sEditor = sSharedPreferences.edit();
    }

    private SharedPreferencesUtil() {
    }

    /**
     * 获取boolean类型
     *
     * @param key        键
     * @param defaultVal 默认值
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultVal) {
        return sSharedPreferences.getBoolean(key, defaultVal);
    }

    /**
     * 获取boolean类型，默认=false
     *
     * @param key 键
     * @return
     */
    public static boolean getBoolean(String key) {
        return sSharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取字符串类型
     *
     * @param key        键
     * @param defaultVal 默认值
     * @return
     */
    public static String getString(String key, String defaultVal) {
        return sSharedPreferences.getString(key, defaultVal);
    }

    /**
     * 获取字符串类型，默认=null
     *
     * @param key 键
     * @return
     */
    public static String getString(String key) {
        return sSharedPreferences.getString(key, null);
    }

    /**
     * 获取Int类型
     *
     * @param key        键
     * @param defaultVal 默认值
     * @return
     */
    public static int getInt(String key, int defaultVal) {
        return sSharedPreferences.getInt(key, defaultVal);
    }


    /**
     * 获取Int类型 默认=0
     *
     * @param key 键
     * @return
     */
    public static int getInt(String key) {
        return sSharedPreferences.getInt(key, 0);
    }

    /**
     * 获取Float类型
     *
     * @param key        键
     * @param defaultVal 默认值
     * @return
     */
    public static float getFloat(String key, float defaultVal) {
        return sSharedPreferences.getFloat(key, defaultVal);
    }

    /**
     * 获取Float类型 默认=0f
     *
     * @param key 键
     * @return
     */
    public static float getFloat(String key) {
        return sSharedPreferences.getFloat(key, 0f);
    }


    /**
     * 获取Long类型
     *
     * @param key        键
     * @param defaultVal 默认值
     * @return
     */
    public static long getLong(String key, long defaultVal) {
        return sSharedPreferences.getLong(key, defaultVal);
    }

    /**
     * 获取Long类型 默认=0L
     *
     * @param key 键
     * @return
     */
    public static long getLong(String key) {
        return sSharedPreferences.getLong(key, 0L);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String key, Set<String> defaultVal) {
        return sSharedPreferences.getStringSet(key, defaultVal);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String key) {
        return sSharedPreferences.getStringSet(key, null);
    }

    public static Map<String, ?> getAll() {
        return sSharedPreferences.getAll();
    }

    public static boolean exists(String key) {
        return sSharedPreferences.contains(key);
    }


    public static void putString(String key, String value) {
        sEditor.putString(key, value);
        sEditor.commit();
    }

    public static void putInt(String key, int value) {
        sEditor.putInt(key, value);
        sEditor.commit();
    }

    public static void putFloat(String key, float value) {
        sEditor.putFloat(key, value);
        sEditor.commit();
    }

    public static void putLong(String key, long value) {
        sEditor.putLong(key, value);
        sEditor.commit();
    }

    public static void putBoolean(String key, boolean value) {
        sEditor.putBoolean(key, value);
        sEditor.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(String key, Set<String> value) {
        sEditor.putStringSet(key, value);
        sEditor.commit();
    }

    public static void putObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            sEditor.putString(key, objectVal);
            sEditor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        if (sSharedPreferences.contains(key)) {
            String objectVal = sSharedPreferences.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    bais.close();
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void remove(String key) {
        sEditor.remove(key);
        sEditor.commit();
    }

    public static void removeAll() {
        sEditor.clear();
        sEditor.commit();
    }
}
