package com.framgia.music_51.data.resource.shareprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefsImpl implements SharePrefsApis {
    private static final String PREFS_NAME = "music_51";
    private static SharePrefsImpl sInstance;
    private SharedPreferences mSharedPreferences;

    public SharePrefsImpl(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
    }

    public static SharePrefsImpl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharePrefsImpl(context);
        }
        return sInstance;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        if (clazz == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (clazz == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        }
        return null;
    }

    @Override
    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear();
    }
}
