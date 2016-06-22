package com.claimant.dev.wheresmybus.parser;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.claimant.dev.wheresmybus.provider.PlatformInfoContract;
import com.claimant.dev.wheresmybus.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by rajeevkr on 5/22/16.
 */

public class PlatformParser {
    private Element mRouteElements = null;
    Context mContext;

    public PlatformParser(Context mContext) {
        this.mContext = mContext;
    }

    public void startDocParsing(String response) {
        if (!Utils.getSyncStatus(mContext)) {

            Document doc = Jsoup.parse(response);
            Elements table = doc.getElementsByTag("table");
            Element row = null;
            for (int i = 0; i < table.size(); i++) {
                row = table.get(i);
                if (row.attr("class").equalsIgnoreCase("routes")) {
                    ParseRoutesTable(row);
                    break;
                }

            }
        }
    }

    private void ParseRoutesTable(Element row) {
        mRouteElements = row;
        boolean isTobeAdded = false;
        Elements platformElements = mRouteElements.getElementsByTag("tr");
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        if (platformElements.size() > 0) {
            Log.d("Rajeev", "rowFound!!!!!");
            isTobeAdded = true;
            Element routeInfoElement = null;
            for (int i = 1; i <= 491; i++) {
                routeInfoElement = platformElements.get(i);
                contentProviderOperations.add(ContentProviderOperation.newInsert(PlatformInfoContract.PlatformItems.CONTENT_URI).
                        withValues(parsePlatforms(routeInfoElement)).build());
            }
        } else {
            //TODO: Handle error here
        }
        try {
            mContext.getContentResolver().applyBatch(PlatformInfoContract.AUTHORITY, contentProviderOperations);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        if (isTobeAdded) {
            Utils.setSyncStatus(mContext);
        }
    }


    private ContentValues parsePlatforms(Element routeInfoElement) {
        Elements busElement = routeInfoElement.getElementsByTag("td");
        Log.d("Rajeev", "id: " + busElement.get(0).text() +
                "Bus: " + busElement.get(1).text() +
                "Routes Addr: " + busElement.get(2).text() +
                "Platform: " + busElement.get(3).text());

        ContentValues contentValues = new ContentValues();

        contentValues.put(PlatformInfoContract.PlatformItems.COLUMN_BUS,
                busElement.get(1).text());
        contentValues.put(PlatformInfoContract.PlatformItems.COLUMN_ROUTE,
                busElement.get(2).text());
        contentValues.put(PlatformInfoContract.PlatformItems.COLUMN_PLATFORM,
                TextUtils.isEmpty(busElement.get(3).text()) || busElement.get(3).text().equalsIgnoreCase("48") ? "100" : busElement.get(3).text());
        return contentValues;

    }


}

