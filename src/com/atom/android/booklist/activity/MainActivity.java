package com.atom.android.booklist.activity;


import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.fragments.LoginFragment;
import com.atom.android.booklist.fragments.MainFragment;

import android.support.v4.app.Fragment;



public class MainActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		UserInfo user = (UserInfo) getIntent().getSerializableExtra(LoginFragment.EXTRA_USER);
		return MainFragment.newInstance(user);
	}



 
}
