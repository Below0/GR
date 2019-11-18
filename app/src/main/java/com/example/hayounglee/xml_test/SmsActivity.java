package com.example.hayounglee.xml_test;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DB.DBHelper;


public class SmsActivity extends AppCompatActivity {
    Bitmap bm;
    TextView message, message_t, message_p;
    TextView mt;
    SharedPreferences prefs;
    String address;
    public final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    SimpleDateFormat date_1;
    SimpleDateFormat date_2;
    SharedPreferences.Editor editor;
    Boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
         check = true;
        date_1 = new SimpleDateFormat("yyyyMMddHHmmss");
        date_2 = new SimpleDateFormat(("yyyy년 MM월 dd일 hh시 mm분"));
        GpsInfo gps = new GpsInfo(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        if(prefs.getBoolean("phonenum", true)){
            if(gps.isGetLocation()) {
                final double lat = gps.getLatitude();
                final double lon = gps.getLongitude();
                address = getAddress(lat, lon);
                Log.e("gps","gpsgps213");
                sendSMS();
            }else{
                Log.e("gps","gpsgps");
                Toast.makeText(getApplicationContext(), "GPS가 꺼져있어 메세지 전송에 실패하였습니다.", Toast.LENGTH_LONG).show();
            }

        }

        //------------------------address사용하면됨(주소)

        mt = (TextView) findViewById(R.id.mt_box);
        message = (TextView) findViewById(R.id.message);
        message_p = (TextView) findViewById(R.id.message_place);
        message_t = (TextView) findViewById(R.id.message_time);
        Intent passedIntent = getIntent();
        processIntent(passedIntent);
        Intent intent = new Intent(this, DialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }

    public void m_click(View v) {
        switch (v.getId()) {
            case R.id.sms_button0:
                Intent goMain = new Intent(getApplicationContext(), MainActivity.class);
                goMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(goMain);
                break;
            case R.id.sms_button1:
                finish();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String sender = intent.getStringExtra("sender");


            new Thread(new Runnable() {
                EqDetector eqDetector = new EqDetector();
                EqInfo eqInfo;

                @Override

                public void run() {

                    // TODO Auto-generated method stub

                    // data= getXmlData(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                    eqInfo = eqDetector.detect();


                    runOnUiThread(new Runnable() {


                        @Override

                        public void run() {
                            DBHelper dbHelper = new DBHelper(SmsActivity.this, "EarthquakeDB", null, 1);
                            dbHelper.testDB();

                            Log.d("db is running", "run: db is running");

                            if(check == true) {
                                Log.e("db", "eqinfo 삽입");
                                dbHelper.putData(eqInfo); //db에다가 넣음
                            check = false;
                            }
                            //class 안에 list 에 출력할 값을 설정합니다.

                            // TODO Auto-generated method stub
                            Log.d("debug", "setText data");
                            // tv.setText(data);  //TextView에 문자열  data 출력
                            new DownloadImageTask((ImageView) findViewById(R.id.sms_iv))
                                    .execute(eqInfo.getImg());
                            double Mt = eqInfo.getMt();
                            String strColor = new String();
                            if (Mt < 2.5) {
                                strColor = "#11CC11";
                            } else if (Mt < 3.5) {
                                strColor = "#CCCC11";
                            } else if (Mt < 4.5) {
                                strColor = "#FF4111";
                            } else {
                                strColor = "#CC1111";
                            }
                            mt.setTextColor(Color.parseColor(strColor));
                            mt.setText(Double.toString(eqInfo.getMt()));

                            String rem = eqInfo.getRem();
                            message_p.setText(eqInfo.getLoc());
                            Date date = null;
                            try {
                                date = date_1.parse(eqInfo.getTmEqk());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            message_t.setText(date_2.format(date));
                            editor.remove("tmeqk");
                           editor.putString("tmeqk", date_2.format(date));
                           editor.commit();
                        }

                    });

                }

            }).start();

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            String image = BitMapToString(result);

            SharedPreferences pref = getSharedPreferences("image", MODE_PRIVATE);

            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            editor.putString("imagestrings", image);
            editor.commit();

        }
    }

    public String BitMapToString(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();

        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;

    }

    private void sendSMS() {
        int cnt = 0;
        String phone = new String();
        Log.e("send","샌드메세지");
        String message = "[지진알리미 GR] 해당 사용자님은 " + address +"에 있습니다.";
        // 권한이 허용되어 있는지 확인한다
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

                Toast.makeText(this, "문자 전송을 위해서는 SMS 발신 권한을 설정해야 합니다.", Toast.LENGTH_LONG).show();
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);

            }
        }
        else{
            SmsManager sms = SmsManager.getDefault();

            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다


            phone = prefs.getString("num1","").toString();
            if(!(phone.equals("")||phone.isEmpty()))  sms.sendTextMessage(phone, null, message, null, null);
            phone = prefs.getString("num2","").toString();
            if(!(phone.equals("")||phone.isEmpty()))  sms.sendTextMessage(phone, null, message, null, null);
        }

    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

                Toast.makeText(this, "문자 전송을 위해서는 SMS 발신 권한을 설정해야 합니다.", Toast.LENGTH_LONG).show();
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "SMS 권한을 사용자가 승인함.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "SMS 권한 거부됨.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    public String getAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }
}