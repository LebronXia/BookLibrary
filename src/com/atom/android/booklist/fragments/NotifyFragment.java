package com.atom.android.booklist.fragments;

import com.atom.android.booklist.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class NotifyFragment extends BaseFragment{
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_notify, container, false);
		return view;
	}
}
