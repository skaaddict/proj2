package com.example.umbcevents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
       // MediaPlayer buttonClickSound = MediaPlayer.create(Splash.this, R.raw.wfreakndo1);
       // buttonClickSound.start();
                
        Thread splashTimer = new Thread(){
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally{ finish(); }
			}
		
		};
		splashTimer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
