package com.atom.android.booklist.activity;

import java.util.List;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.BrrowedHistoryFragment;

public class BrrowedHistoryActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<BookSerachRe> historyBooks = (List<BookSerachRe>) getIntent().getSerializableExtra(BrrowedHistoryFragment.EXTRA_HISTORYBOOKS);
		return BrrowedHistoryFragment.newInstance(historyBooks);
	}

}
