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
	protected Context mContext;
	protected MediaPlayer mMediaPlayer;
	protected ImageButton[] buttons = new ImageButton[6];
	protected TypedArray sounds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = getApplicationContext();

		getButtonViews();
		setButtonClicks();
		sounds = getResources().obtainTypedArray(R.array.sound_resources);

		mMediaPlayer = MediaPlayer.create(this, R.raw.tech_spawn_02);
		mMediaPlayer.start();
		mMediaPlayer.setOnCompletionListener(this);
	}
	
	@Override
	protected void onDestroy () {
		super.onDestroy();
		
		if (this.isFinishing()) {
			sounds.recycle();
			mMediaPlayer.release();
		}
	}

	@Override
    public void onCompletion(MediaPlayer mp) {
        setContentView(R.layout.activity_main);
    }

	private void getButtonViews() {
		buttons[0] = (ImageButton)this.findViewById(R.id.imageButton1);
		buttons[1] = (ImageButton)this.findViewById(R.id.imageButton2);
//		buttons[2] = (ImageButton)this.findViewById(R.id.imageButton3);
//		buttons[3] = (ImageButton)this.findViewById(R.id.imageButton4);
//		buttons[4] = (ImageButton)this.findViewById(R.id.imageButton5);
//		buttons[5] = (ImageButton)this.findViewById(R.id.imageButton6);
	}
	
	private void setButtonClicks() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] != null) {
				buttons[i].setTag(i);
				buttons[i].setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Log.d(null, "clicked view: " + v.getId());
						playSound(v);
					}
				});
			}
		}
	}

	private void playSound(View button) {
		int soundId = sounds.getResourceId((int) button.getTag(), 0);
		playSound(Uri.parse("android.resource://com.example.soundboard/" + soundId));
	}

	private void playSound(Uri uri) {
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
