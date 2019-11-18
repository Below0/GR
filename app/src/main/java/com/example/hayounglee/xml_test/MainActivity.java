package com.example.hayounglee.xml_test;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import DB.DBHelper;
import DB.ListAdapter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    SharedPreferences prefs;
    TextView[] st_text;
    String data;
    ShelterInfo[] si;
    ImageButton[] m_button;
    LinearLayout[] menu;
    WebView  wv;
    ImageView main_iv;
    LinearLayout st_info;
    Button[] etcBtn;
    ImageView favor;
    SharedPreferences.Editor editor;
    ImageButton fav_btn;
    TextView tmeqk;
    public final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;
    public final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    public final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessReceiveSms = false;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;


    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;

    private Marker marker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private AppCompatActivity mActivity;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition;
    ListView record;
    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
    private DBHelper dbHelper;
    private SharedPreferences pref1;

    public double[] markerLoc = new double[2];
    private String[] markerAdd = new String[1];
    public List people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.activity_main);

        // 메인 이미지 불러오기
        favor = (ImageView) findViewById(R.id.favorite);
        st_info = (LinearLayout) findViewById(R.id.shelterinfo);
        main_iv = (ImageView) findViewById(R.id.main_iv2);
        pref1 = getSharedPreferences("image", MODE_PRIVATE);
      //
        //send sms permission----------------------
       // callPermission();
        //-----------------------------------------
        tmeqk = (TextView) findViewById(R.id.tmeqk);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
         editor = prefs.edit();
        st_text = new TextView[4];
        st_text[0] = (TextView)findViewById(R.id.st_id);
        st_text[1] = (TextView) findViewById(R.id.st_address);
        st_text[2] = (TextView) findViewById(R.id.st_x);
        st_text[3] = (TextView) findViewById(R.id.st_y );
        fav_btn = (ImageButton)findViewById(R.id.fav_btn);
        if(!(prefs.getString("fav_name","null").equals("null"))){
            Log.e("asdasd", "here");
            fav_btn.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onCreate");
        mActivity = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.i("debug", "11111111111");

        menu = new LinearLayout[4];
        menu[0] = (LinearLayout) findViewById(R.id.menu0); menu[3] = (LinearLayout) findViewById(R.id.menu3);
        menu[1] = (LinearLayout) findViewById(R.id.menu1); menu[2] = (LinearLayout) findViewById(R.id.menu2);
        m_button = new ImageButton[4];
        m_button[0] = (ImageButton) findViewById(R.id.mButton_0); m_button[1] = (ImageButton)findViewById(R.id.mButton_1);
        m_button[2] = (ImageButton) findViewById(R.id.mButton_2); m_button[3] = (ImageButton)findViewById(R.id.mButton_3);

        dbHelper = new DBHelper(MainActivity.this, "EarthquakeDB", null, 1);

        record = (ListView) findViewById(R.id.record);

       // record.setVisibility(View.VISIBLE);
     //   Toast.makeText(getApplicationContext(), "record.setVisibility ", Toast.LENGTH_SHORT).show();

        //        //DB Helper 가 NULL 이면 초기화 시켜준다.
        if (dbHelper == null) {
            dbHelper = new DBHelper(MainActivity.this, "TEST_TABLE", null, 1);
        }


        // 1.Person 데이터를 모두 가져온다.
        people = dbHelper.getAllPersonData();

        // 2.ListView에 Person데이터를 모두 보여준다
        record.setAdapter(new ListAdapter(people, MainActivity.this));


    }


    @Override
    public void onResume() {

        super.onResume();
        if(!(prefs.getString("fav_name","null").equals("null"))){
            fav_btn.setVisibility(View.VISIBLE);
        }
        if (mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }

        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;
                checkPermissions();
            }
        }
        people = dbHelper.getAllPersonData();

        // 2.ListView에 Person데이터를 모두 보여준다
        record.setAdapter(new ListAdapter(people, MainActivity.this));
        String image =  pref1.getString("imagestrings", "");
        Bitmap bitmap = StringToBitMap(image);
        main_iv.setImageBitmap(bitmap);
        tmeqk.setText(prefs.getString("tmeqk","최근 정보 없음"));
    }

    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call FusedLocationApi.requestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            mRequestingLocationUpdates = true;

            mGoogleMap.setMyLocationEnabled(true);

        }

    }



    private void stopLocationUpdates() {

        Log.d(TAG,"stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady :");

        mGoogleMap = googleMap;


        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("asdasd",marker.getId()+"           "+currentMarker.getId());
                if(marker.getId().equals(currentMarker.getId())) return false;
                if(marker.getTitle().equals(prefs.getString("fav_name",null))){
                    favor.setBackgroundResource(R.drawable.star_fill);
                }
                else{
                    favor.setBackgroundResource(R.drawable.star);
                }
                    st_text[0].setText("" + marker.getTitle());
                    st_text[1].setText(getCurrentAddress(marker.getPosition()));
                    st_text[2].setText(Double.toString(marker.getPosition().latitude));
                    st_text[3].setText(Double.toString(marker.getPosition().longitude));
                    st_info.setVisibility(View.VISIBLE);
                    return false;

                }

        });
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){

            @Override
            public boolean onMyLocationButtonClick() {
                mGoogleMap.clear();
                Log.d( TAG, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");
                mMoveMapByAPI = true;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentMarker.getPosition());
                mGoogleMap.animateCamera(cameraUpdate);
                return true;
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                st_info.setVisibility(View.GONE);
                Log.d( TAG, "onMapClick :");
            }
        });

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {

                if (mMoveMapByUser == true && mRequestingLocationUpdates){

                    Log.d(TAG, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }

                mMoveMapByUser = true;

            }
        });


        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {


            }
        });

        //longclick시 마커생성
        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mGoogleMap.clear();
                if(marker != null){
                    marker.remove();
                }
                // 현재 위도와 경도에서 화면 포인트를 알려준다
                Point screenPt = mGoogleMap.getProjection().
                        toScreenLocation(latLng);

                // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
                final LatLng point = mGoogleMap.getProjection().
                        fromScreenLocation(screenPt);
                String address = getCurrentAddress(point);
                markerLoc[0] = point.latitude;
                markerLoc[1] = point.longitude;
                markerAdd[0] = address;
                Log.e("asd", "위도 경도"+markerLoc[0] +"     "+markerLoc[1]);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions
                        .position(latLng)
                        .title(address);
                marker = mGoogleMap.addMarker(markerOptions);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {

        currentPosition
                = new LatLng( location.getLatitude(), location.getLongitude());


        Log.d(TAG, "onLocationChanged : ");

        String markerTitle = getCurrentAddress(currentPosition);
        /*String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                + " 경도:" + String.valueOf(location.getLongitude());
        //위도,경도 뜨게하고싶으면 주석 해제 후 setCurrentLocation(location,makerTitle,makerSnippet);하기
        */

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, "현재위치");

        mCurrentLocatiion = location;
    }


    @Override
    protected void onStart() {

        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() == false){

            Log.d(TAG, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onStop() {

        if (mRequestingLocationUpdates) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            stopLocationUpdates();
        }

        if ( mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }


    @Override
    public void onConnected(Bundle connectionHint) {


        if ( mRequestingLocationUpdates == false ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                } else {

                    Log.d(TAG, "onConnected : 퍼미션 가지고 있음");
                    Log.d(TAG, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    mGoogleMap.setMyLocationEnabled(true);
                }

            }else{

                Log.d(TAG, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed");
        setDefaultLocation();
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.d(TAG, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }


    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
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


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        mMoveMapByUser = false;


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mGoogleMap.addMarker(markerOptions);


        if ( mMoveMapByAPI ) {

            Log.d( TAG, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }


    public void setDefaultLocation() {

        mMoveMapByUser = false;


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.");
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");

            if ( mGoogleApiClient.isConnected() == false) {

                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogForPermissionSetting(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : 퍼미션 가지고 있음");


                        if ( mGoogleApiClient.isConnected() == false ) {

                            Log.d( TAG, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }

                break;
        }
    }


    public void permssionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "지진 정보 수신을 위해서는 SMS 수신 권한을 설정해야 합니다.", Toast.LENGTH_LONG).show();
            } else {
// No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

        Log.i("debug", "after callpermission");
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //send permission

        //---------------
        if (requestCode == MY_PERMISSIONS_REQUEST_RECEIVE_SMS
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessReceiveSms = true;

        }
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation && isAccessFineLocation) {
            isPermission = true;
        }


        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {

            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (permissionAccepted) {


                if ( mGoogleApiClient.isConnected() == false) {

                    Log.d(TAG, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }



            } else {

                checkPermissions();
            }
        }


    }
    public void etcBtnClick(View v){
        switch(v.getId()){
            case R.id.settingBtn:
                startActivity(new Intent(MainActivity.this,
                        settingActivity.class));
                break;
            case R.id.build_check:
                String url = "http://www.aurum.re.kr/KoreaEqk/SelfChkStart";
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(web);
                break;
            case R.id.safekorea:
                    String url2 =  "http://www.safekorea.go.kr/idsiSFK/neo/main/main.html";
                    Intent web2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                    startActivity(web2);
                    break;
            case R.id.eqaction:
                startActivity(new Intent(MainActivity.this,
                        Eqaction.class));


        }
    }

    public void menu_Click(View v){
        int i = 0;
        int check = 0;
        switch (v.getId()) {
            case R.id.mButton_0:
                check = 0;
                break;
            case R.id.mButton_1:
                check = 1;
                break;
            case R.id.mButton_2:
                check = 2;
                break;

            case R.id.mButton_3:
                check = 3;
                break;
        }
        for(i = 0; i < menu.length; i++){
            if(i == check){
                menu[i].setVisibility(View.VISIBLE);
                m_button[i].setBackgroundResource(R.drawable.select_border);
            }
            else{
                m_button[i].setBackgroundResource(R.drawable.non_select_border);
                menu[i].setVisibility(View.GONE);
            }
        }
    }
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.favorite:
                if(st_text[0].getText().equals(prefs.getString("fav_name",null))){
                    fav_btn.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,st_text[0].getText().toString()+" 즐겨찾기 해제합니다",Toast.LENGTH_SHORT).show();
                    favor.setBackgroundResource(R.drawable.star);
                    editor.remove("fav_name");
                    editor.remove("fav_address");
                    editor.remove("fav_lat");
                    editor.remove("fav_lon");
                }
                else {
                    Toast.makeText(MainActivity.this,st_text[0].getText().toString()+" 즐겨찾기 등록합니다",Toast.LENGTH_SHORT).show();
                    favor.setBackgroundResource(R.drawable.star_fill);
                    fav_btn.setVisibility(View.VISIBLE);
                    editor.remove("fav_name");
                    editor.remove("fav_address");
                    editor.remove("fav_lat");
                    editor.remove("fav_lon");
                    editor.putString("fav_name", "" + st_text[0].getText());
                    editor.putString("fav_address", "" + st_text[1].getText());
                    editor.putString("fav_lat", "" + st_text[2].getText());
                    editor.putString("fav_lon", "" + st_text[3].getText());
                }
                editor.commit();
                break;

            case R.id.fav_btn:
                //즐겨찾기 버튼클릭
                if(prefs.getString("fav_name","null").equals("null")){
                    return;
                }
                    favor.setBackgroundResource(R.drawable.star_fill);
                    st_text[0].setText("" + prefs.getString("fav_name",null));
                    st_text[1].setText(""+prefs.getString("fav_address",null));
                    st_text[2].setText(""+prefs.getString("fav_lat",null));
                    st_text[3].setText(""+prefs.getString("fav_lon",null));
                    st_info.setVisibility(View.VISIBLE);
                double lat1 = Double.parseDouble(st_text[2].getText().toString());
                double lon1 = Double.parseDouble(st_text[3].getText().toString());
                LatLng loc = new LatLng(lat1,lon1);
                MarkerOptions makerOptions = new MarkerOptions();
                makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                        .position(new LatLng(lat1, lon1))
                        .title(st_text[1].getText().toString()); // 마커누르면 나올 string

                // 마커 만들기
                mGoogleMap.addMarker(makerOptions);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(loc));

                    break;
            case R.id.button:

                //Android 4.0 이상 부터는 네트워크를 이용할 때 반드시 Thread 사용해야 함
                Log.d("debug", "thread before");
                GpsInfo gps = new GpsInfo(MainActivity.this);


                if(gps.isGetLocation());
                else{
                    gps.showSettingsAlert();
                    return ;
                }
                final double lat = gps.getLatitude();
                  final double lon = gps.getLongitude();
                //------------
                if(markerLoc[0] == 0 && markerLoc[1] == 0){
                    markerLoc[0] = lat;
                    markerLoc[1] = lon;
                }
                final double latitude = markerLoc[0];
                final double longitude = markerLoc[1];
                //------------




                if(latitude == 0.0 && longitude == 0.0){
                    Log.e("error", "위도 경도 정보 없음");
                    gps.stopUsingGPS();
                    return ;
                }
                Log.i("asdasd", "위도:"+latitude+"경도:"+longitude);
                Toast.makeText(MainActivity.this, "탐색을 시작합니다.", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        StDetector sd = new StDetector(latitude, longitude);
                        // TODO Auto-generated method stub

                        si = sd.getXmlData();
                        runOnUiThread(new Runnable() {

                            @Override

                            public void run() {

                                // TODO Auto-generated method stub
                                Log.d("debug", "setText data");
                                // tv.setText(data);  //TextView에 문자열  data 출력
                               //tv.setText("주소 :  " + si[0].getAddress() + "\n이름 : " + si[0].getName() + "\n 위도, 경도 : (" + si[0].getLat() + "," + si[0].getLon() + ")\n탐색된 대피소 수: "+si.length+"개");


                               for(int i = 0; i < si.length; i++){
                                    double lat = Double.parseDouble(si[i].getLat());//파싱해온 위도 받아서 lat에 형변환 후 넣기
                                    double lon = Double.parseDouble(si[i].getLon());
                                    String shelterName = si[i].getName();//파싱해온 주소 넣기
                                    MarkerOptions makerOptions = new MarkerOptions();
                                    makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                                            .position(new LatLng(lat, lon))
                                            .title(shelterName); // 마커누르면 나올 string

                                    // 마커 만들기
                                    mGoogleMap.addMarker(makerOptions);

                                }
                            }

                        });

                    }

                }).start();

                break;

        }
    }//mOnClick method..

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        String[] request = null;
        int cnt = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            cnt++;
            requestPermissions(
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_RECEIVE_SMS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            cnt++;

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
cnt++;
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
        request = new String[cnt];

    }
    public Bitmap StringToBitMap(String encodedString){

        try{

            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);

            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            return bitmap;

        }catch(Exception e){

            e.getMessage();

            return null;

        }




    }
//sms send퍼미션-----------------------
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("권한이 필요합니다.")
                        .setMessage("이 기능을 사용하기 위해서는 단말기의 \"문자전송\" 권한이 필요합니다. 계속하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1000);
                                }
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
            //최초로 권한을 요청할 때
            else {
                // CALL_PHONE 권한을 Android OS 에 요청한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1000);
                    }
            }
        }
    }


}

