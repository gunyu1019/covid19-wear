package kr.yhs.covid.activity

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import com.devs.vectorchildfinder.VectorChildFinder
import kr.yhs.covid.R

class DistancingMapActivity: MainResourceActivity() {
    private lateinit var map: VectorChildFinder
    private lateinit var imageView: ImageView

    private fun mapCreate(defaultColor: Int = Color.GRAY) {
        val locations: Array<String> = arrayOf(
            "Seoul",
            "Busan",
            "Daegu",
            "Incheon",
            "Gwangju",
            "Daejeon",
            "Ulsan",
            "Gyeonggi",
            "Gangwon",
            "North Chungcheong",
            "South Chungcheong",
            "North Jeolla",
            "South Jeolla",
            "North Gyeongsang",
            "South Gyeongsang",
            "Jeju",
            "Sejong",
        )
        for (location in locations) {
            val path = map.findPathByName(location)
            path.fillColor = defaultColor
        }

        imageView.invalidate()
    }

    private fun mapEditColor(path: String, defaultColor: Int = Color.GRAY) {
        val pathD = map.findPathByName(path)
        pathD.fillColor = defaultColor
        imageView.invalidate()
    }

    override fun onCreate() {
        imageView = view.findViewById<ImageView>(R.id.mapView)
        map = VectorChildFinder(this.context, R.drawable.korea_map, imageView)
        mapCreate()
    }
}