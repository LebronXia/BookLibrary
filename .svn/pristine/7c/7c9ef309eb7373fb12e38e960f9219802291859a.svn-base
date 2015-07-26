package com.atom.android.booklist.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

public class SystemUtils {

	public static int getVesion(Context context) {

		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
