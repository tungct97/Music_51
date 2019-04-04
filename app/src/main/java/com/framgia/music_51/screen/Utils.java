package com.framgia.music_51.screen;

public class Utils {
    public static final String URL =
            "https://api-v2.soundcloud.com/search/tracks?q=%s" +
                    "&sc_a_id=e59d651b-5b9e-4eec-8e2a-0f98c3eb0567&variant_ids=" +
                    "&facet=genre&user_id=909695-112147-772262-91428" +
                    "&client_id=Zju0N4zmkkZ60kMBebep9nP703ozMTpx&limit=20&offset=0&" +
                    "linked_partitioning=1&app_version=1532422103&app_locale=en";
    public static final String KIND = "top";
    public static final String COLON = ":";
    public static final int TYPE_LOCAL = 1;
    public static final int TYPE_REMOTE = 2;
    public static final String FOLDER = "FOLDER_NAME";
    public static final String TRACK = "track";
    public static final String MP3 = ".mp3";
    public static final String DOWNLOAD = "Download/";
}
