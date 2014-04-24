package com.comp1008.group26.FlaxmanGallery;

import java.lang.reflect.InvocationTargetException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.comp1008.group26.FlaxmanGallery.R;
import com.comp1008.group26.utility.SimpleTextView;
public class SettingActivity extends Activity implements OnClickListener {
	public static int fontSize=1;
	public void onCreate(Bundle savedInstanceState) {
	       /* if ("Large".equalsIgnoreCase( getIntent().getStringExtra( "Theme" )))
	        {
	            setTheme(R.style.Theme_Large);
	        }
	        else if ("Small".equalsIgnoreCase( getIntent().getStringExtra( "Theme" )))
	        {
	            setTheme(R.style.Theme_Small);
	        }*/
		
			if (SettingActivity.fontSize==2)
	        {
	            setTheme(R.style.Theme_Large);
	        }
	        else if (SettingActivity.fontSize==1)
	        {
	            setTheme(R.style.Theme_Small);
	        }
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_setting);
	        

			if (SettingActivity.fontSize==2)
			{
	            (( RadioButton ) findViewById( R.id.textSizeLarge )).setChecked(true);
	            (( RadioButton ) findViewById( R.id.textSizeSmall )).setChecked(false);
	        
	        }
	        else if (SettingActivity.fontSize==1)
	        {
	            (( RadioButton ) findViewById( R.id.textSizeSmall )).setChecked(true);
	            (( RadioButton ) findViewById( R.id.textSizeLarge  )).setChecked(false);
	        }
			
	        
	        (( RadioButton ) findViewById( R.id.textSizeLarge )).setOnClickListener(this);
	        (( RadioButton ) findViewById( R.id.textSizeSmall )).setOnClickListener(this);
	        /*
	        RadioButton largeText = ( RadioButton ) findViewById( R.id.textSizeLarge );
		        largeText.setOnClickListener( new OnClickListener() {
		            public void onClick( View view ) {
		               // Toast.makeText(, "Large Text Selected", Toast.LENGTH_SHORT).show();
		           Intent intent = getIntent();
		           intent.putExtra( "Theme", "Large" );
		           fontSize=2;
		           finish();
		           startActivity(intent);
		       }
		   } );
		
		   RadioButton smallText = ( RadioButton ) findViewById( R.id.textSizeSmall );
		   smallText.setOnClickListener( new OnClickListener() {
		       public void onClick( View view ) {
		          // Toast.makeText(context, "Small Text Selected", Toast.LENGTH_SHORT).show();
		           Intent intent = getIntent();
		           intent.putExtra( "Theme", "Small" );
		           fontSize=1;
		           finish();
		           startActivity(intent);
		       }
		   } );*/
	}
	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		((Button) findViewById(R.id.buttonSave)).setOnClickListener(this);
		
		
		getResources().getConfiguration().fontScale = (float) 1.3;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{

         case R.id.buttonSave:
         {
     		
     		 SimpleTextView.setGlobalSize(40);
     		 finish();
        	 break;
         }
   
        }
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
        {
            case R.id.textSizeLarge:
            {
		        fontSize=2;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textSizeSmall:
            {
            	fontSize=1;
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
	}
	
	

}
