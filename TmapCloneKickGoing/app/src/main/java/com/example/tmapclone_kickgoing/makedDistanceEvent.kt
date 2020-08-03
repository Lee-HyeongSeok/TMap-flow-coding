package com.example.tmapclone_kickgoing

// 2020-07-29(수) 작성
// 킥고잉과 자신의 현재위치 사이 거리를 알아내기 위한 클래스 정의

class makedDistanceEvent {
    class DistanceEvent(){} // 생성자

    public fun CalcDistance(lat1:Double, lon1:Double, lat2:Double, lon2:Double): Int{

        var EARTH_R: Double = 6371000.0
        var Rad: Double = Math.PI/180
        var radLat1: Double = Rad*lat1
        var radLat2: Double = Rad*lat2
        var radDist: Double = Rad*(lon1 - lon2)
        var distance:Double = Math.sin(radLat1) * Math.sin(radLat2)
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist)
        var ret:Double = EARTH_R * Math.acos(distance)
        var rslt:Long = Math.round(Math.round(ret).toDouble() / 1000);
        var result = Math.round(ret).toInt() /* M 계산 */
        return result;

    }
}