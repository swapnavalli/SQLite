package com.valli.sqlrecyclermvp;

import android.provider.BaseColumns;

public class Shoppinglist_entity {

    public Shoppinglist_entity(){}

    public static final class Shoppinglist_entries implements BaseColumns{
        public static final String TABLE_NAME = "shoppinglist_table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
