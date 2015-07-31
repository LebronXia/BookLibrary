package com.atom.android.booklist.activity;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookInfoResult;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.DetailFragment;

public class DetailActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		BookSerachRe bookSerachRe = (BookSerachRe) getIntent().getSerializableExtra(DetailFragment.EXTRA_BOOKINFODATAIL);
		return DetailFragment.newInstance(bookSerachRe);
	}

}
