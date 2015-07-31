package com.atom.android.booklist.activity;

import java.util.List;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.SearchFragment;
import com.atom.android.booklist.fragments.SearchResultFragmnet;

public class SearchResultActivity extends SingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<BookSerachRe> bookSearchReList = (List<BookSerachRe>) getIntent()
				.getSerializableExtra(SearchResultFragmnet.EXTRA_BOOK_SERCHRESULT);
		return SearchResultFragmnet.newInstance(bookSearchReList);
	}

}
