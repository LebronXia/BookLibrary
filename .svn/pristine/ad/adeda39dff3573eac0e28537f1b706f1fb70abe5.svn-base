package com.atom.android.booklist.utils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import com.atom.android.booklist.interfaces.OnTaskListener;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

public class NetUtils {
	private FinalHttp finalHttp;
	private OnTaskListener mOnTaskListener;
	
	
	
	public OnTaskListener getOnTaskListener() {
		return mOnTaskListener;
	}

	public void setmOnTaskListener(OnTaskListener mOnTaskListener) {
		this.mOnTaskListener = mOnTaskListener;
	}
	public FinalHttp get(final int taskflag,String sessionId,String url){
		finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		finalHttp.configTimeout(30000);
		finalHttp.configRequestExecutionRetryCount(3);
		finalHttp.addHeader("Cookie", "JSESSIONID="+sessionId);
		finalHttp.get(url, params, new AjaxCallBack() {
			public void onLoading(long total, long current)
			{
				getOnTaskListener().onLoading(taskflag,total, current);
			}

			public void onStart()
			{
				//getOnTaskListener().onstart();
			}

			public void onSuccess(Object result)
			{
				getOnTaskListener().onSuccess(result, taskflag);
			}

			public void onFailure(Throwable error, String msg)
			{
				getOnTaskListener().onFailure(error, msg, taskflag);
			}
		});
		return finalHttp;
	}
	public FinalHttp get(final int taskflag,String url)
	{
		return get(taskflag,"",url);
	} 

	
	public FinalHttp post(Map map, final int taskflag, String url)
			throws Exception
		{
			finalHttp = new FinalHttp();
			AjaxParams params = new AjaxParams();
			finalHttp.configTimeout(30000);
			finalHttp.configRequestExecutionRetryCount(3);
			
			Iterator it = map.entrySet().iterator();  
	        while (it.hasNext())  
	        {  
	        	Map.Entry entry = (Map.Entry)it.next();
	        	params.put((String)entry.getKey(), (String)entry.getValue());
	        }  
			finalHttp.post(url, params, new AjaxCallBack() {

				public void onLoading(long total, long current)
				{
					if (mOnTaskListener != null)
						getOnTaskListener().onLoading(taskflag,total, current);
				}

				public void onSuccess(Object result)
				{
					if (mOnTaskListener != null)
						getOnTaskListener().onSuccess(result, taskflag);
				}

				public void onFailure(Throwable error, String msg)
				{
					if (mOnTaskListener != null)
						getOnTaskListener().onFailure(error, msg, taskflag);
				}

				public void onStart()
				{
					/*if (mOnTaskListener != null)
						getOnTaskListener().onstart();*/
				}

			});
			return finalHttp;
		}

	
	public HttpHandler download(final int taskflag,String url ,String target){
		
		FinalHttp fh = new FinalHttp();  
	    //调用download方法开始下载
	    HttpHandler handler = fh.download(url, //这里是下载的路径
	    	    target, //这是保存到本地的路径
			    true,//true:断点续传 false:不断点续传（全新下载）
			    new AjaxCallBack() {
	      
	    public void onLoading(long count, long current) {  
	    	getOnTaskListener().onLoading(taskflag,count, current);
	    }  
	 
	    public void onSuccess(File t) {  
	    	getOnTaskListener().onSuccess(t, taskflag);
	    }  
	 
	    });  
	    return handler;
	}
	
	
}
