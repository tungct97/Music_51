package com.framgia.music_51.screen.timer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.framgia.music_51.R;
import com.framgia.music_51.screen.service.TrackService;

public class TimerFragment extends BottomSheetDialogFragment
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static final String TAG = "TimerFragment";
    private RadioGroup mRadioGroupTimer;
    private TrackService mService;
    private boolean mÍsBinded;
    private EditText mTextCustom;
    private TextView mTextTime;
    private ControlMusic mListener;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) service;
            mÍsBinded = true;
            mService = binder.getService();
            mListener = (ControlMusic) mService.getListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
        connectService();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mÍsBinded) {
            getActivity().unbindService(mConnection);
            mÍsBinded = false;
        }
    }

    private void connectService() {
        Intent intent = new Intent(getActivity(), TrackService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                View bottomSheetInternal =
                        d.findViewById(android.support.design.R.id.design_bottom_sheet);
                if (bottomSheetInternal == null) {
                    return;
                }
                bottomSheetInternal.setBackground(null);
                final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheetInternal);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dialog_timer, container, false);
        mRadioGroupTimer = rootView.findViewById(R.id.radioGroup_timer);
        mTextCustom = rootView.findViewById(R.id.text_confirm);
        rootView.findViewById(R.id.confirm).setOnClickListener(this);
        mRadioGroupTimer.setOnCheckedChangeListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                int time = Integer.parseInt(mTextCustom.getText().toString()) * 10000 * 6;
                mListener.countDown(mTextTime, time);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_30:

                mListener.countDown(mTextTime, 1800000);
                break;
            case R.id.radio_60:
                mListener.countDown(mTextTime, 3600000);
                break;
            case R.id.radio_90:
                mListener.countDown(mTextTime, 5400000);
                break;
        }
    }
}
