package com.comp1008.group26.FlaxmanGallery;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TextActivity extends Activity implements OnClickListener {

	String title;
	String body;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_text);

		title = getIntent().getExtras().getString("title");
		body = getIntent().getExtras().getString("body");
		
		((TextView)findViewById(R.id.title)).setText( title);
		((TextView)findViewById(R.id.body)).setText( body);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.text, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
}
