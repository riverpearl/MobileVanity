package com.vanity.mobilevanity.cosmetic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.vanity.mobilevanity.MainActivity;
import com.vanity.mobilevanity.MyApplication;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.alert.AlertActivity;
import com.vanity.mobilevanity.data.DBContract;
import com.vanity.mobilevanity.manager.DBManager;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.util.DateCalculator;

import java.util.Calendar;

public class CosmeticReceiver extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!PropertyManager.getInstance().getIsAlertReceptible())
            return;

        Cursor c = DBManager.getInstance().selectCosmeticItem();

        long cosmeticId = 0;
        String notiMessage = "";

        while (c.moveToNext()) {
            String regDate = c.getString(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_REG_DATE));
            int useby = c.getInt(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_TERM));

            DateCalculator calculator = new DateCalculator();
            int dday = calculator.calculateDDay(regDate, useby);

            long cid = c.getLong(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_SERVER_ID));
            String name = c.getString(c.getColumnIndex(DBContract.CosmeticItem.COLUMN_COSMETIC_NAME));
            String message = name + "의 사용기한이 " + dday + "일 남았습니다.";
            String date = calculator.CalToStr(Calendar.getInstance());

            if (dday == 30 || dday == 10 || dday == 3 || dday == 1) {
                DBManager.getInstance().insertNotify("useby", cid, message, date);
                cosmeticId = cid;
                notiMessage = message;
            }
        }

        if (!TextUtils.isEmpty(notiMessage) && cosmeticId > 0)
            sendNotification(cosmeticId, notiMessage);

    }

    private void sendNotification(long cid, String message)
    {
        Intent intent = new Intent(MyApplication.getContext(), CosmeticDetailActivity.class);
        intent.putExtra(CosmeticDetailActivity.TAG_COSMETIC_ITEM_ID, cid);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getContext(), 1, intent, PendingIntent.FLAG_ONE_SHOT);

        int bgColor = MyApplication.getContext().getResources().getColor(R.color.colorMain);

        BitmapDrawable drawable = (BitmapDrawable) MyApplication.getContext().getResources().getDrawable(R.drawable.vanity_logo_big);
        Bitmap appIcon = drawable.getBitmap();

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyApplication.getContext())
                .setLargeIcon(appIcon)
                .setSmallIcon(R.drawable.icon_notify_dday)
                .setColor(bgColor)
                .setTicker("Useby Message")
                .setContentTitle("Vanity")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)MyApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
