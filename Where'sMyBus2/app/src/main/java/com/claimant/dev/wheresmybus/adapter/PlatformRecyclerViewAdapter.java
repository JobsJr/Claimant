package com.claimant.dev.wheresmybus.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claimant.dev.wheresmybus.R;

/**
 * Created by rajeevkr on 5/29/16.
 */

public class PlatformRecyclerViewAdapter extends CursorRecyclerViewAdapter<PlatformRecyclerViewAdapter.ItemViewHolder> {
    private int mCount;
    private Context mContext;

    public PlatformRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mCount = cursor.getCount();
        mContext = context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return mCount;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            viewHolder.busNumberText.setText(cursor.getString(1));
            viewHolder.platformNumberText.setText(cursor.getString(2));
            viewHolder.routeAddressText.setText(cursor.getString(3));
        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView busNumberText;
        TextView platformNumberText;
        TextView routeAddressText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            busNumberText = (TextView) itemView.findViewById(R.id.tv_bus_num);
            platformNumberText = (TextView) itemView.findViewById(R.id.tv_platform);
            routeAddressText = (TextView) itemView.findViewById(R.id.tv_via);

        }
    }
}
