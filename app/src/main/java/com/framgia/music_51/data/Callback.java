package com.framgia.music_51.data;

public interface Callback<T> {
    void getDataSuccess(T movies);

    void getDataFail(Exception e);
}
