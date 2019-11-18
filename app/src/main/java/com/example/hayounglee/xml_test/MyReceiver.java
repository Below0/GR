package com.example.hayounglee.xml_test;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {
    SharedPreferences prefs;
    public static final String TAG = "SmsReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        prefs =  PreferenceManager.getDefaultSharedPreferences(context);
        if(prefs.getBoolean("DETECT_ON",true)==false){
            return;
        }
        Log.i(TAG, "onReceive() 메소드 호출됨.");
// 인텐트 안에 들어있는 SMS 메시지를 파싱합니다.
        String sender = null;
        String contents = null;
        Date receivedDate = null;
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle); // android.telephony.SmsMessage
        if (messages != null && messages.length > 0) {
// SMS 발신 번호 확인
            sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender : " + sender);
            if((sender.equals("긴급재난문자")||sender.equals("긴급 재난 문자"))||sender.equals("cbs")||sender.equals("01072074538")||sender.equals("01084111642")){
                Log.i("asdasd","asdasdasdasd");

            }
            else{
                return;
            }
// SMS 메시지 확인
            contents = messages[0].getMessageBody().toString();
            Log.i(TAG, "SMS contents : " + contents);
            if(contents.contains("지진")){
                Log.i("asdasdasd", "지진 키워드 포함");
            }
            else return;
// SMS 수신 시간 확인
            receivedDate = new Date(messages[0].getTimestampMillis()); // java.util.Date
            Log.i(TAG, "SMS received date : " + receivedDate.toString());
        }
        sendToActivity(context, sender, contents, receivedDate);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        int smsCount = objs.length;
        for (int i = 0; i < smsCount; i++) {
// PDU 포맷으로 되어 있는 메시지를 복원합니다.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // API 23 이상
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }

    private void sendToActivity(Context context, String sender, String contents, Date receivedDate) {
        Intent myIntent = new Intent(context, SmsActivity.class);
// 플래그를 이용합니다.
        myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
