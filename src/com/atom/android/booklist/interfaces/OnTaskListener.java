package com.atom.android.booklist.interfaces;

public interface OnTaskListener{
	public void onLoading(int taskflag,long total, long current);
	
	public abstract void onSuccess(Object obj, int taskflag);

	public abstract void onFailure(Throwable throwable, String s, Object obj);
	
		
}
