package com.example.stn.stn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Name: CommonAdapter
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-11 14:23.
 */
public abstract class CommonAdapter<T> extends BaseAdapter
{
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;

    public CommonAdapter(Context context, List<T> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

}
