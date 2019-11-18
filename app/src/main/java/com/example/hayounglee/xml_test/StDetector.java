package com.example.hayounglee.xml_test;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class StDetector {
    private double latitude;
    private double longitude;


    public StDetector(double lat, double lon){
    this.latitude = lat;
    this.longitude = lon;
    }

    ShelterInfo[] getXmlData(){

        DistanceCal dc = new DistanceCal();
        int size;
        Boolean SIZE_CHECK = false;
        ShelterInfo[] array = null;
        int cnt = 0;
        int diff = 500; // 최소 측정 거리 500M 반경
        String min_x, min_y, max_x, max_y;
        double lat_dc, lon_dc;

        while(SIZE_CHECK == false) {
            lat_dc = dc.LatInDif(diff);
            lon_dc = dc.LoninDif(longitude, diff);

            min_x = Double.toString(latitude - lat_dc);
            max_x = Double.toString(latitude + lat_dc);
            min_y = Double.toString(longitude - lon_dc);
            max_y = Double.toString(longitude + lon_dc);

            String queryUrl = "http://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_P_EDRSE002&key=0786D02B-00E1-3859-83A7-B1C0E28C841B&domain=http://localhost:8080&format=xml" +
                    "&geomFilter=BOX(" + min_y + "," + min_x + "," + max_y + "," + max_x + ")";  //요청 URL
            Log.i("log", "주소" + queryUrl);
            try {

                URL url = new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.

                InputStream is = url.openStream();  //url위치로 입력스트림 연결

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

                String tag;
                xpp.next();

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    switch (eventType) {

                        case XmlPullParser.START_DOCUMENT:

                            break;

                        case XmlPullParser.START_TAG:

                            Log.e("START_TAG", "START");

                            tag = xpp.getName();    //테그 이름 얻어오기
                            if (tag.equals("current") && SIZE_CHECK == false) {

                                xpp.next();
                                size = Integer.parseInt(xpp.getText());
                                if(size == 0){
                                    diff += 250; // 만약 검색 값이 NULL 일시 탐색반경 250M  증가
                                    Log.e("diff", "diff="+diff);
                                    continue;
                                }

                                else if( size > 0) {
                                    array = new ShelterInfo[size];
                                    SIZE_CHECK = true;
                                    Log.e("sizesize", "Size =" + Integer.toString(size));
                                }
                            }
                            if (tag.equals("result")) ;// 첫번째 검색결과
                            else if (tag.equals("lat")) {
                                array[cnt] = new ShelterInfo();
                                array[cnt].setDistance(diff);
                                Log.e("debug", "Lat");
                                xpp.next();
                                array[cnt].setLat(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            } else if (tag.equals("lon")) {
                                Log.e("debug", "lon");
                                xpp.next();

                                array[cnt].setLon(xpp.getText()); //telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가

                            } else if (tag.equals("shel_nm")) {
                                Log.e("debug", "name");
                                xpp.next();

                                array[cnt].setName(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            } else if (tag.equals("shel_ad")) {
                                xpp.next();

                                array[cnt].setAddress(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                cnt++;
                            }
                            Log.w("asdasd", "cnt = " + cnt);
                            break;

                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:

                            tag = xpp.getName();    //테그 이름 얻어오기

                            break;
                    }
                    eventType = xpp.next();

                }

            } catch (Exception e) {

                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            // Log.w("dbug", "array[0]="+array[cnt].getName());
        }
        return array;

    }

}
