package com.atom.android.booklist.activity;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.fragments.LoginFragment;

import android.support.v4.app.Fragment;

public class LoginActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new LoginFragment(getApplicationContext());
	}

}
