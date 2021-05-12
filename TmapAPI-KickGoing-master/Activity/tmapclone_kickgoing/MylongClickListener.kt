package org.techtown.mapservicewithtmapapi

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.util.Log
import com.example.tmapclone_kickgoing.R
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

class MylongClickListener(tmap: TMapView, pointArr : ArrayList<TMapPoint>, arrM: ArrayList<TMapMarkerItem>, context: Context) : TMapView.OnLongClickListenerCallback, Application(), TMapView.OnCalloutRightButtonClickCallback {
    var tmapthis = tmap     // TMap
    var pointSpEP = pointArr        // 출발지, 목적지 TmapPoint가 들어간 ArrayList
    var contextthis = context       // 리스너가 발동된 상위에서의 Context
    var arrMthis = arrM         // 기존의 존재하던 마커 목록


    // 길게 눌렀을 때 작동하는 메서드
    override fun onLongPressEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint) {
        // 마커 아이콘
        var temp: TMapMarkerItem = TMapMarkerItem()     // 마커 생성
        temp.tMapPoint = p2     // 화면상에서 클릭된 포인트 정보를, 마커에 대입
        temp.visible = TMapMarkerItem.VISIBLE       // 마커의 보임 상태 지정
        temp.canShowCallout = true      // 마커 클릭시 풍선 뷰 보임 설정
        temp.calloutRightButtonImage = BitmapFactory.decodeResource(contextthis.resources, R.drawable.kickicon)   // 마커 풍선뷰에 이미지 등록
        arrMthis.add(temp)      // 기존에 가지고 있던 마커 리스트에 마커 추가

        tmapthis.setIconVisibility(true)
        tmapthis.setSightVisible(true)
        tmapthis.setTrackingMode(true)

        for (n in 0..(arrMthis.size - 1)) {
            tmapthis.addMarkerItem("marker${n}", arrMthis.get(n))       // 마커 리스트에 존재하는 모든 마커를, 맵에 등록
        }
    }

    // 마커 풍선뷰에 등록해둔 이미지가 선택되었을 때 발생
    override fun onCalloutRightButton(p0: TMapMarkerItem?) {
        if (p0 != null) {
            var dialog = AlertDialog.Builder(contextthis)       // 다이얼로그 창을 통해 마커를 눌렀을 때 수행할 수 있는 목록들 보여줌
            var markerItems = arrayOf<String>("출발지 지정", "목적지 지정", "마커삭제")
            var markerMenuListener = object : DialogInterface.OnClickListener {     // 다이얼로그 메뉴 중 1개를 눌렀을 때 발생
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        0 -> {  // 출발지 지정시
                            pointSpEP[0] = p0.tMapPoint
                        }
                        1 -> {  // 목적지 지정시
                            pointSpEP[1] = p0.tMapPoint
                        }
                        2 -> {  // 마커 삭제 시
                            for (i in 0..(arrMthis.size - 1)) {
                                var id = arrMthis.get(i).id
                                if (id == p0.id) {
                                    arrMthis.removeAt(i)
                                    tmapthis.removeMarkerItem(id)
                                    tmapthis.invalidate()
                                    break
                                }
                            }
                        }
                    }
                }
            }

            dialog.setItems(markerItems, markerMenuListener)
            dialog.show()

        } else {
        }
    }
}