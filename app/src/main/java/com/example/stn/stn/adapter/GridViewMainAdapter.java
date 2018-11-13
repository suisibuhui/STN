package com.example.stn.stn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.example.stn.stn.R;

import java.util.List;

/**
 * Name: GridViewMainAdapter
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-11 23:43.
 */
public abstract class GridViewMainAdapter<T> extends CommonAdapter<T> {

    public GridViewMainAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                R.layout.item_gridview_homepage, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);
}
