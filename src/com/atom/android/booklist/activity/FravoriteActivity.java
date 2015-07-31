package com.atom.android.booklist.activity;

import java.util.List;

import android.support.v4.app.Fragment;

import com.atom.android.booklist.activity.base.SingleFragmentActivity;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.fragments.FavoriteFragment;
import com.atom.android.booklist.fragments.ReadingFragment;

public class FravoriteActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		List<BookSerachRe> CollectList = (List<BookSerachRe>) getIntent().getSerializableExtra(FavoriteFragment.EXTRA_FAVORITE);
		return FavoriteFragment.newInstance(CollectList);
	}

}
