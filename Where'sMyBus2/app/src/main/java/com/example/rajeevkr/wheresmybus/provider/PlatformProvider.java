package com.example.rajeevkr.wheresmybus.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by rajeevkr on 5/22/16.
 */

public class PlatformProvider extends ContentProvider {
    private PlatformDBOpenHelper mPlatformDBOpenHelper;


    @Override
    public boolean onCreate() {
        mPlatformDBOpenHelper = PlatformDBOpenHelper.getInstanse(getContext());
        return mPlatformDBOpenHelper != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mPlatformDBOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        switch (PlatformInfoContract.sUriMatcher.match(uri)) {
            case PlatformInfoContract.PLATFORMS:
                sqLiteQueryBuilder.setTables(PlatformDBOpenHelper.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = PlatformInfoContract.PlatformItems.DEFAULT_SORT_ORDER;
                }
                break;
            case PlatformInfoContract.PLATFORM:
                sqLiteQueryBuilder.setTables(PlatformDBOpenHelper.TABLE_NAME);
                sqLiteQueryBuilder.appendWhere(PlatformInfoContract.PlatformItems._ID + " = " +
                        uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("invalid uri: " + uri);
        }

        Cursor cursor = sqLiteQueryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), PlatformInfoContract.PlatformItems.CONTENT_URI);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = mPlatformDBOpenHelper.getWritableDatabase();
        long id = 0;
        switch (PlatformInfoContract.sUriMatcher.match(uri)) {
            case PlatformInfoContract.PLATFORMS:
                id = sqLiteDatabase.insert(PlatformDBOpenHelper.TABLE_NAME, null, values);
                break;
            case PlatformInfoContract.PLATFORM:
                id = sqLiteDatabase.insertWithOnConflict(PlatformDBOpenHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return getUriForId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mPlatformDBOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (PlatformInfoContract.sUriMatcher.match(uri)) {
            case PlatformInfoContract.PLATFORMS:
                rowsDeleted = sqLiteDatabase.delete(PlatformDBOpenHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case PlatformInfoContract.PLATFORM:
                String rowId = uri.getLastPathSegment();
                String where = PlatformInfoContract.PlatformItems._ID + "=" + rowId;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                rowsDeleted = sqLiteDatabase.delete(PlatformDBOpenHelper.TABLE_NAME, where, selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mPlatformDBOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (PlatformInfoContract.sUriMatcher.match(uri)) {
            case PlatformInfoContract.PLATFORMS:
                rowsUpdated = sqLiteDatabase.update(PlatformDBOpenHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PlatformInfoContract.PLATFORM:
                String rowId = uri.getLastPathSegment();
                String where = PlatformInfoContract.PlatformItems._ID + "=" + rowId;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                rowsUpdated = sqLiteDatabase.update(PlatformDBOpenHelper.TABLE_NAME, values, where, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    public Uri getUriForId(Uri contentUri, long id) {
        if (id > 0) {
            Uri platformUri = ContentUris.withAppendedId(contentUri, id);
            getContext().getContentResolver().notifyChange(platformUri, null);
            return platformUri;
        }
        throw new SQLException(" Problem while inserting in to uri " + contentUri);
    }
}
