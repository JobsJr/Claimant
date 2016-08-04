package com.claimant.dev.wheresmybus.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claimant.dev.wheresmybus.R;

/**
 * Created by rajeevkr on 5/29/16.
 */

public class PlatformGridRecyclerViewAdapter extends CursorRecyclerAdapter<PlatformGridRecyclerViewAdapter.ItemViewHolder> {
    private int mCount;
    private Context mContext;
    public static final int ITEM_TYPE_PLATFORM = 1;
    public static final int ITEM_TYPE_BUS = ITEM_TYPE_PLATFORM + 1;

    public PlatformGridRecyclerViewAdapter(Context context, Cursor cursor) {
        super(cursor);
        mCount = cursor.getCount();
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_platform_grid, parent, false);
        return new ItemViewHolder(itemView);

    }


    @Override
    public void onBindViewHolderCursor(ItemViewHolder holder, Cursor cursor) {
        onBindItemViewHolder(holder, cursor);

    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private void onBindItemViewHolder(ItemViewHolder viewHolder, Cursor cursor) {
        String busNumber = "";
        if (cursor != null) {
            String platformNumber = "";
            platformNumber = cursor.getString(2);
            viewHolder.platformNumber.setText(platformNumber);

            busNumber = cursor.getString(1);
            if (busNumber.contains(",")) {
                busNumber.replaceAll(",", "");
            }
            viewHolder.busNumberText.setText(busNumber);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView busNumberText;
        TextView platformNumber;


        public ItemViewHolder(View itemView) {
            super(itemView);

            busNumberText = (TextView) itemView.findViewById(R.id.text_bus_num);
            platformNumber = (TextView) itemView.findViewById(R.id.text_platform_num);

        }
    }
}
