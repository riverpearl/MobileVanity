package com.vanity.mobilevanity.data;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2016-09-01.
 */
public class DBContract {
    public interface CosmeticItem extends BaseColumns {
        public static final String TABLE = "cosmeticitem";
        public static final String COLUMN_SERVER_ID = "sid";
        public static final String COLUMN_COSMETIC_ID = "cid";
        public static final String COLUMN_REG_DATE = "regdate";
        public static final String COLUMN_USEBY_DATE = "usebydate";
        public static final String COLUMN_TERM = "term";
    }

    public interface Notify extends BaseColumns {
        public static final String TABLE = "notify";
        public static final String COLUMN_COSMETIC_ITEM_ID = "cosmeticitemid";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_DATE = "date";
    }
}
