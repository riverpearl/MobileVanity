package com.vanity.mobilevanity.cosmetic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.vanity.mobilevanity.MainActivity;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.data.DBContract;
import com.vanity.mobilevanity.manager.DBManager;
import com.vanity.mobilevanity.util.DateCalculator;

public class CosmeticReceiver extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, CosmeticDetailActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);

        Cursor c = DBManager.getInstance().selectCosmeticItem();

        Log.d("c count", c.getCount() + "");

        while (c.moveToNext()) {
            String regDate = c.getString(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_REG_DATE));
            int cosmeticTerm = c.getColumnIndex(DBContract.CosmeticItem.COLUMN_TERM);

            DateCalculator calculator = new DateCalculator();
            int term = calculator.calculateDDay(regDate, cosmeticTerm);

            int cid = c.getColumnIndex(DBContract.CosmeticItem.COLUMN_COSMETIC_ID);
            String name = c.getString(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_COSMETIC_NAME));
            String message = name + "의 사용기한이 " + cosmeticTerm + "일 남았습니다.";
            String date = c.getString(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_USEBY_DATE));

            if (term == 240 || term == 10 || term == 3 || term == 1) {
                DBManager.getInstance().insertNotify(cid, message, date);
            }
            builder.setSmallIcon(android.R.drawable.star_on).setTicker("COSMETIC").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("Vanity").setContentText(message)
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

            notificationManager.notify(1, builder.build());

        }

    }
}
