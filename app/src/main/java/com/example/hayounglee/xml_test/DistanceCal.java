package com.example.hayounglee.xml_test;

public class DistanceCal {
    final int earth = 6371000; // 지구 반지름 단위 m
    public double LatInDif(int diff){ // 반경 m이내의 위도차


        return (diff*360.0) / (2*Math.PI*earth);
    }

    //반경 m이내의 경도차(degree)
    public double LoninDif(double _latitude, int diff){
        //지구반지름
        final int earth = 6371000;    //단위m

        double ddd = Math.cos(0);
        double ddf = Math.cos(Math.toRadians(_latitude));

        return (diff*360.0) / (2*Math.PI*earth*Math.cos(Math.toRadians(_latitude)));
    }

}
