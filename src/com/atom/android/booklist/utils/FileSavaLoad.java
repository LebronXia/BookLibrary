package com.atom.android.booklist.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FileSavaLoad {

	private Context mContext;
	private String mFileName;
	
	public FileSavaLoad(Context c, String f) {
		mContext = c;
		mFileName = f;
	}

	/**±£´æÎÄ¼þ
	 * @param data
	 * @param mContext
	 */
	public void save(String data){
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			out = mContext.openFileOutput(mFileName, mContext.MODE_APPEND);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(data+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if(writer != null){
					writer.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> load(){
		FileInputStream in = null;
		BufferedReader reader = null;
		List<String> mlist= new ArrayList<String>();
		try {
			in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null){
				mlist.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(reader != null){
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}
		return mlist;
	}
}
