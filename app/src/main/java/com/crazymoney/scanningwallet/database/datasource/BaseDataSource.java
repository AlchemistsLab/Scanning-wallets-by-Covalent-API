package com.crazymoney.scanningwallet.database.datasource;

/**
 * Created by daniel_nguyen on 6/19/14.
 */
public class BaseDataSource {
    private static final String TAG = BaseDataSource.class.getSimpleName();

    protected static boolean getBooleanFromAffectedRows(int affectedRows) {
        if (affectedRows > 0) {
            return true;
        }
        return false;
    }
}
