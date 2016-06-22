package com.claimant.dev.wheresmybus.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claimant.dev.wheresmybus.R;

/**
 * Created by rajeevkr on 5/29/16.
 */

public class PlatformRecyclerViewAdapter extends CursorRecyclerAdapter<PlatformRecyclerViewAdapter.ItemViewHolder> {
    private int mCount;
    private Context mContext;

    public PlatformRecyclerViewAdapter(Context context, Cursor cursor) {
        super(cursor);
        if (cursor == null) mCount = 0;
        else
            mCount = cursor.getCount();
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolderCursor(ItemViewHolder viewHolder, Cursor cursor) {
        String busNumber = "";
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.getString(2).equalsIgnoreCase("100")) {
                viewHolder.platformContainer.setVisibility(View.GONE);
                return;
            } else {
                viewHolder.platformContainer.setVisibility(View.VISIBLE);
                viewHolder.platformNumberText.setText(cursor.getString(2));
            }

            busNumber = cursor.getString(1);
            if (busNumber.contains(",")) {
                busNumber.replaceAll(",", "");
            }
            viewHolder.busNumberText.setText(busNumber);
            viewHolder.routeAddressText.setText(cursor.getString(3));
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView busNumberText;
        TextView platformNumberText;
        TextView routeAddressText;
        View platformContainer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            busNumberText = (TextView) itemView.findViewById(R.id.tv_bus_num);
            platformNumberText = (TextView) itemView.findViewById(R.id.tv_platform);
            routeAddressText = (TextView) itemView.findViewById(R.id.tv_via);
            platformContainer = itemView.findViewById(R.id.item_container);

        }
    }
}
