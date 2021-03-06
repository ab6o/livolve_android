package com.hackday.livolve.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.hackday.livolve.DialogType;
import com.hackday.livolve.fragment.LivolveDialogFragment;

public abstract class LivolveActivity extends Activity implements IActivity{

	protected String TAG = getTag();
	protected Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!shouldShowActionBar())
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	protected void goTo(Class class1){
		goTo(class1,null);
	}

	protected void goTo(Class class1,Bundle extras){
		Intent intent = new Intent(this,class1);
		if(extras!=null)
			intent.putExtras(extras);
		startActivity(intent);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if(shouldExitOnNewActivityLaunch())
			finish();
	}

	protected void showMyDialog(String message,String tag,DialogType type){
		DialogFragment dialogFragment = new LivolveDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("id", type.getId());
		bundle.putString("message", message);
		dialogFragment.setArguments(bundle);

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(dialogFragment, tag);
		transaction.commitAllowingStateLoss();
	}

	protected void removeMyDialog(String tag){
		Fragment fragment = getFragmentManager().findFragmentByTag(tag);
		if(fragment!=null)
		{
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.remove(fragment);
			transaction.commitAllowingStateLoss();
		}
	}
}
