/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vanity.mobilevanity.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.vanity.mobilevanity.MainActivity;
import com.vanity.mobilevanity.R;
import com.vanity.mobilevanity.beautytip.BeautyTipDetailActivity;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.Notify;
import com.vanity.mobilevanity.manager.DBManager;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.NotifyListRequest;
import com.vanity.mobilevanity.util.DateCalculator;

import java.util.Calendar;
import java.util.List;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (!PropertyManager.getInstance().getIsAlertReceptible())
            return;

        final String type = data.getString("type");

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            String lastDate = PropertyManager.getInstance().getLastNotifyDate();

            NotifyListRequest request = new NotifyListRequest(this, lastDate);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<List<Notify>>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<List<Notify>>> request, NetworkResult<List<Notify>> result) {
                    if (result.getCode() == 1) {
                        List<Notify> notifies = result.getResult();

                        for (Notify n : notifies) {
                            DBManager.getInstance().insertNotify(type, n.getBeautyTipId().getKey().getRaw().getId(), n.getMessage(), n.getDate());
                        }

                        Notify notify = notifies.get(notifies.size() - 1);
                        sendNotification(notify);

                        Calendar newLastDateTemp = Calendar.getInstance();
                        DateCalculator calculator = new DateCalculator();
                        String newLastDate = calculator.CalToStr(newLastDateTemp);
                        PropertyManager.getInstance().setLastNotifyDate(newLastDate);
                    }
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<List<Notify>>> request, int errorCode, String errorMessage, Throwable e) {

                }
            });
        }

        //sendNotification(type, message, beautyTipId);
    }

    private void sendNotification(Notify notify) {
        Intent intent = new Intent(this, BeautyTipDetailActivity.class);
        intent.putExtra(BeautyTipDetailActivity.TAG_BEAUTY_TIP_ID, notify.getBeautyTipId().getKey().getRaw().getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("GCM Message")
                .setContentTitle("Vanity")
                .setContentText(notify.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
