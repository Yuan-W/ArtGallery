package com.comp1008.group26.FlaxmanGallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.view.Window;

import com.comp1008.group26.utility.DbxSyncConfig;
import com.comp1008.group26.utility.FileSyncTask;
import com.dropbox.sync.android.*;

import java.util.List;

import static com.comp1008.group26.utility.DbxSyncConfig.getAccountManager;

/**
 * @author HO Sze Nga (s.ho.13@ucl.ac.uk), Yuan Wei
 */
public class MainActivity extends Activity implements View.OnClickListener {
	private DbxAccountManager mDbxAcctMgr;
	private static final String LOG_TAG = "ART_GALLERY";

	View decorView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (SettingActivity.fontSize == 2) {
			setTheme(R.style.Theme_Large);
		} else if (SettingActivity.fontSize == 1) {
			setTheme(R.style.Theme_Small);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(uiOptions);

		mDbxAcctMgr = getAccountManager(this);

		((ImageButton) findViewById(R.id.start_btn)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.setting_btn)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.update_btn)).setOnClickListener(this);

		findViewById(R.id.update_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onClickUpdate();
					}
				});

		linkToDropbox();
	}

	private void onClickUpdate() {
		try {
			CharSequence[] charSequence = getArmatureList();

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Folders").setItems(charSequence,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int armatureIndex) {
							updateFile(dialog, armatureIndex);
						}

					});
			AlertDialog dialog = builder.create();
			dialog.show();
		} catch (DbxException e) {
			e.printStackTrace();
		}
	}

	private CharSequence[] getArmatureList() throws DbxException {
		List<DbxFileInfo> folderList = DbxFileSystem.forAccount(
				mDbxAcctMgr.getLinkedAccount()).listFolder(DbxPath.ROOT);
		CharSequence[] charSequence = new CharSequence[folderList.size()];
		int i = 0;
		for (DbxFileInfo info : folderList) {
			charSequence[i++] = info.path.getName();
		}
		return charSequence;
	}

	private void updateFile(DialogInterface dialog, int armatureIndex) {
		String selectedArmature = ((AlertDialog) dialog).getListView()
				.getItemAtPosition(armatureIndex).toString();
		Log.d(LOG_TAG, selectedArmature);
		DbxPath syncPath = DbxPath.ROOT.getChild(selectedArmature);

		new FileSyncTask(this, mDbxAcctMgr, syncPath).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_btn: {
			Intent intent = new Intent(this, StartActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.setting_btn: {
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			break;
		}
		}

	}

	private void linkToDropbox() {
		if (!mDbxAcctMgr.hasLinkedAccount()) {
			mDbxAcctMgr.startLink(this, DbxSyncConfig.REQUEST_LINK_TO_DBX);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {

			decorView
					.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
																	// bar
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

}
