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

public class PlatformGridRecyclerViewAdapter extends CursorRecyclerViewAdapter<PlatformGridRecyclerViewAdapter.ItemViewHolder> {
    private int mCount;
    private Context mContext;
    public static final int ITEM_TYPE_PLATFORM = 1;
    public static final int ITEM_TYPE_BUS = ITEM_TYPE_PLATFORM + 1;
    private String mLastPlatform = "";
    private Cursor mCursor;

    public PlatformGridRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mCount = cursor.getCount();
        mCursor = cursor;
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(mContext).inflate(R.layout.item_platform_grid, parent, false);
        return new ItemViewHolder(itemView);

    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, Cursor cursor) {
        onBindItemViewHolder(viewHolder, cursor);
    }


    private void onBindItemViewHolder(ItemViewHolder viewHolder, Cursor cursor) {
        String busNumber = "";
        if (cursor != null) {
            String platformNumber = "";
            if (mLastPlatform.equalsIgnoreCase("") || mLastPlatform.equalsIgnoreCase(cursor.getString(2))) {
                viewHolder.platformContainer.setVisibility(View.VISIBLE);
                platformNumber = cursor.getString(2);
                viewHolder.platformNumber.setText(platformNumber);
                mLastPlatform = platformNumber;
            } else {
                viewHolder.platformContainer.setVisibility(View.GONE);
            }

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
        View platformContainer;


        public ItemViewHolder(View itemView) {
            super(itemView);
            busNumberText = (TextView) itemView.findViewById(R.id.text_bus_num);
            platformNumber = (TextView) itemView.findViewById(R.id.text_platform_number);
            platformContainer = itemView.findViewById(R.id.layout_platform_container);

        }
    }
}
