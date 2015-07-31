package com.atom.android.booklist.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;


public class BaseFragment extends Fragment {
	protected void showMsg(Context mContext,String msg){
    	Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
    }
	protected void openActivity(Class<?> pClass)
	{
		Intent intent = new Intent();
		intent.setClass(getActivity(), pClass);
		startActivity(intent);
	}
}
