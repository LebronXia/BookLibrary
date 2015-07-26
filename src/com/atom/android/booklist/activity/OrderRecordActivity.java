package com.atom.android.booklist.activity;

import java.util.List;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.OrderRecordFragment;
import com.atom.android.booklist.fragments.ReadingFragment;

public class OrderRecordActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<BookSerachRe> OrderBooks = (List<BookSerachRe>) getIntent().getSerializableExtra(OrderRecordFragment.EXTRA_ORDERRECORD);
		return OrderRecordFragment.newInstance(OrderBooks);
	}

}
