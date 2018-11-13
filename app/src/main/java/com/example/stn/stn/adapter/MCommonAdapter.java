package com.example.stn.stn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Name: MCommonAdapter
 * Author: xulong
 * Comment: //公共adapter
 * Date: 2016-08-08 11:02.
 */
public abstract class MCommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public MCommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final MViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        
        return viewHolder.getConvertView();

    }





    private MViewHolder getViewHolder(int position, View convertView,
                                      ViewGroup parent)
    {
        return MViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public abstract void convert(MViewHolder helper, T item, int position);

}
