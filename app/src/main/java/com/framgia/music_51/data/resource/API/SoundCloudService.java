package com.framgia.music_51.data.resource.API;

public class SoundCloudService {
    public static ApiRequest getGenreService() {
        return SoundCloudApiUtils.getClient().create(ApiRequest.class);
    }
}
