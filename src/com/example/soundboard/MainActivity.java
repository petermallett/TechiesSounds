package com.example.soundboard;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener {
	private static String sPkgName;

	protected Context mContext;
	protected MediaPlayer mMediaPlayer;
	protected ImageButton[] mButtons = new ImageButton[6];
	protected TypedArray mSounds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sPkgName = this.getPackageName();

		mMediaPlayer = MediaPlayer.create(this, R.raw.tech_spawn_02);
		mMediaPlayer.start();
		mMediaPlayer.setOnCompletionListener(this);
		
		mContext = getApplicationContext();
	}
	
	@Override
	protected void onDestroy () {
		super.onDestroy();
		
		if (this.isFinishing()) {
			mSounds.recycle();
			mMediaPlayer.release();
		}
	}

	@Override
    public void onCompletion(MediaPlayer mp) {
        setContentView(R.layout.activity_main);
    }

	/**
	 * Plays the sound referenced by the button's tag property.
	 */
	public void buttonClickPlaySound(View v) {
		int resId = getResources().getIdentifier(
				(String) v.getTag(), "raw", sPkgName); 

		Uri uri = Uri.parse("android.resource://" + sPkgName + "/" + resId);

	    try {
	        mMediaPlayer.reset();
	        mMediaPlayer.setDataSource(mContext, uri);
	        mMediaPlayer.prepare();
	        mMediaPlayer.start();
	    } catch (IOException e) {
	        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
	    }
	}
}
