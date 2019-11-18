package com.example.hayounglee.xml_test;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class EqDetector {
    public EqInfo detect(){

            EqInfo eqinfo = new EqInfo();

            int cnt = 0;
            String fcTp = new String();
            String queryUrl="http://newsky2.kma.go.kr/service/ErthqkInfoService/EarthquakeReport?ServiceKey=0f2to44ZSBP527qwIw6lc6b0cRp22W2ThXgWnrfMGyXeaLSHv7fswO0997FTVjRP38E5jNoHB0m0mZZuNDQx%2BQ%3D%3D&fromTmFc=20190410";  //요청 URL
        Log.e("debug", "detect");
            try {

                URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.

                InputStream is= url.openStream();  //url위치로 입력스트림 연결

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();

                XmlPullParser xpp= factory.newPullParser();

                xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

                String tag;
                xpp.next();

                int eventType= xpp.getEventType();
                while( eventType != XmlPullParser.END_DOCUMENT ){

                    switch( eventType ){

                        case XmlPullParser.START_DOCUMENT:

                            break;

                        case XmlPullParser.START_TAG:


                            tag= xpp.getName();    //테그 이름 얻어오기

                            if(tag.equals("item"));// 첫번째 검색결과
                            if(tag.equals("fcTp")) {
                                xpp.next();
                                fcTp = xpp.getText();
                            }
                            if(fcTp.equals("3") || fcTp.equals("11")||fcTp.equals("5")) { // 국내 경보, 조기 국내 경보, 국내 재경보 정보만 가져온다.
                            if (tag.equals("img")) {
                                    Log.e("debug", "Lat");
                                    xpp.next();
                                    eqinfo.setImg(xpp.getText());
                                } else if (tag.equals("lat")) {
                                    Log.e("debug", "lon");
                                    xpp.next();
                                    eqinfo.setLat(Double.parseDouble(xpp.getText()));
                                } else if (tag.equals("loc")) {
                                    xpp.next();
                                    eqinfo.setLoc(xpp.getText());
                                } else if (tag.equals("lon")) {
                                    Log.e("debug", "name");
                                    xpp.next();
                                    eqinfo.setLon(Double.parseDouble(xpp.getText()));
                                } else if (tag.equals("mt")) {
                                    Log.e("debug", "address");
                                    xpp.next();

                                    eqinfo.setMt(Double.parseDouble(xpp.getText()));

                                } else if (tag.equals("rem")) {
                                    Log.e("debug", "address");
                                    xpp.next();
                                    eqinfo.setRem(xpp.getText());

                                } else if (tag.equals("tmEqk")) {
                                    Log.e("debug", "Lat");
                                    xpp.next();
                                    eqinfo.setTmEqk(xpp.getText());
                                    cnt++;
                                }
                            }
                            break;

                        case XmlPullParser.TEXT:

                            break;

                        case XmlPullParser.END_TAG:

                            tag= xpp.getName();    //테그 이름 얻어오기



                            break;

                    }
                    eventType= xpp.next();

                }

            } catch (Exception e) {

                // TODO Auto-generated catch block
                Log.d("debug", "예외 발생");
                e.printStackTrace();
            }

        return eqinfo;

        }
    }

