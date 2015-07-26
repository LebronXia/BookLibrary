package com.atom.android.booklist.activity;

import java.util.List;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.ReadingFragment;

import android.support.v4.app.Fragment;

public class ReadingActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<BookSerachRe> booksInReading = (List<BookSerachRe>) getIntent().getSerializableExtra(ReadingFragment.EXTRA_BorrowList);
		return ReadingFragment.newInstance(booksInReading);
	}

}
