package com.atom.android.booklist.activity;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.fragments.NotifyFragment;

public class notifyActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new NotifyFragment();
	}

}
