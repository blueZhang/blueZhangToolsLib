package com.bluezhang.bitmapdisplay_1012_class.adadapterutil;

public interface MultiItemTypeSupport<T> {
	int getLayoutId(int position, T t);

	int getViewTypeCount();

	int getItemViewType(int postion, T t);
}