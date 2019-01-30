package com.framgia.music_51.data.resource.shareprefs;

public interface SharePrefsApis {
    <T> T get(String key, Class<T> clazz);

    <T> void put(String key, T data);

    void clear();
}
