package com.claimant.dev.wheresmybus.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rajeevkr on 5/22/16.
 */

public final class PlatformInfoContract {

    private static final String SCHEMA = "content://";
    public static final String AUTHORITY = "com.claimant.dev.wheremybus.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY);

    static final int PLATFORMS = 1;
    static final int PLATFORM = 2;

    static final UriMatcher sUriMatcher;


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, PlatformItems.PATH, PLATFORMS);
        sUriMatcher.addURI(AUTHORITY, PlatformItems.PATH + "/#", PLATFORM);
    }

    public static final class PlatformItems implements BaseColumns {

        private static final String PATH = "platforms";


        /************
         * COLUMN INFO
         ***********/
        public static final String COLUMN_BUS = "bus";
        public static final String COLUMN_ROUTE = "route";
        public static final String COLUMN_PLATFORM = "platform";

        //Add the content uri for PlatformItems table
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public static final String[] PROJECTION_ALL = {PlatformItems._ID, PlatformItems.COLUMN_BUS,
                PlatformItems.COLUMN_PLATFORM,
                PlatformItems.COLUMN_ROUTE
        };
        public static final String DEFAULT_SORT_ORDER = "CAST (" + PlatformItems.COLUMN_PLATFORM + " AS INTEGER)" + " ASC";


    }


}
