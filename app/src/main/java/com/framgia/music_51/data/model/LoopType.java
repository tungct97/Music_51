package com.framgia.music_51.data.model;

import android.support.annotation.IntDef;

import static com.framgia.music_51.data.model.LoopType.LOOP_ALL;
import static com.framgia.music_51.data.model.LoopType.LOOP_ONE;
import static com.framgia.music_51.data.model.LoopType.NO_LOOP;

@IntDef({NO_LOOP, LOOP_ONE, LOOP_ALL})
public @interface LoopType {
    int NO_LOOP = 0;
    int LOOP_ONE = 1;
    int LOOP_ALL = 2;
}
