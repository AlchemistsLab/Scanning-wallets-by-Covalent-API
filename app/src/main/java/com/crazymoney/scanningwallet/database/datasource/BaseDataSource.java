package com.crazymoney.scanningwallet.database.datasource;

/**
 * Created by daniel_nguyen on 6/19/14.
 */
public class BaseDataSource {

    protected static boolean getBooleanFromAffectedRows(int affectedRows) {
        return affectedRows > 0;
    }
}
