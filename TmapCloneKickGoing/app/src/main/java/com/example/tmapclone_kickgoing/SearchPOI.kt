package org.techtown.mapservicewithtmapapi

import android.R
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.*
import com.skt.Tmap.*
import kotlinx.android.synthetic.main.activity_find_load.*
import org.w3c.dom.Text
import java.lang.Exception

class SearchPOI(flag:Int, listview: ListView, address: String, context:Context, StartAddress:TextView, EndAddress:TextView, arrP:ArrayList<TMapPoint>) : AsyncTask<Unit, Unit, Boolean>() {

    var flagthis = flag
    var listviewthis = listview
    var addressthis = address
    var contextthis = context
    var listviewAdaper = ArrayAdapter(contextthis, android.R.layout.simple_list_item_1, ArrayList<String>())
    var arrSearchResult = ArrayList<TMapPOIItem>()
    var startAddressthis = StartAddress
    var endAddressthis = EndAddress
    var arrPthis = arrP

    override fun doInBackground(vararg params: Unit?): Boolean {
        try {
            arrSearchResult = TMapData().findAllPOI(addressthis)
        } catch (E : Exception){
            Log.d("NO search","검색 결과가 없습니다")
        }
        var temp = ArrayList<String>()
        for (i in 0..arrSearchResult.size-1){
            temp.add(arrSearchResult[i].name)
        }
        listviewAdaper = ArrayAdapter(contextthis, android.R.layout.simple_list_item_1, temp)

        return true
    }


    override fun onPostExecute(result: Boolean?) {
        listviewthis.adapter = listviewAdaper

        listviewthis.setOnItemClickListener { parent, view, position, id ->
//            var checkedIndex = listviewthis.checkedItemPosition
            var checkedItem = arrSearchResult.get(position)
            if (startAddressthis.isFocused) {
                startAddressthis.text = checkedItem.name
                arrPthis[0] = checkedItem.poiPoint
            }
            else if (endAddressthis.isFocused){
                endAddressthis.text = checkedItem.name
                arrPthis[1] = checkedItem.poiPoint
            }


        }

//

    }
}