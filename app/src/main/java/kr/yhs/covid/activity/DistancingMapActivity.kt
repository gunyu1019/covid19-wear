package kr.yhs.covid.activity

import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import com.devs.vectorchildfinder.VectorChildFinder
import kotlinx.coroutines.*
import kr.yhs.covid.R
import org.jsoup.Jsoup
import kotlin.coroutines.CoroutineContext

class DistancingMapActivity: MainResourceActivity(), CoroutineScope {
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
        mJob = Job()
        imageView = view.findViewById(R.id.mapView)
        map = VectorChildFinder(this.context, R.drawable.korea_map, imageView)
        mapCreate()

        launch {
            val deffered = async(Dispatchers.Default) {
                val response = Jsoup.connect(
                    "http://ncov.mohw.go.kr/"
                ).get()
                val mainMapLayout = response.body().select("div#main_maplayout1")
                val locations: Array<String> = arrayOf(
                    "Seoul",
                    "Busan",
                    "Daegu",
                    "Incheon",
                    "Gwangju",
                    "Daejeon",
                    "Ulsan",
                    "Sejong",
                    "Gyeonggi",
                    "Gangwon",
                    "North Chungcheong",
                    "South Chungcheong",
                    "North Jeolla",
                    "South Jeolla",
                    "North Gyeongsang",
                    "South Gyeongsang",
                    "Jeju",
                )
                val result = mutableMapOf<String, String>()
                for ((index, location) in locations.withIndex()) {
                    //Log.i("distancing", "$index - ${mainMapLayout
                    //    .select("button[data-city='step_map_city${index+1}']")}")
                    result[location] = mainMapLayout
                        .select("button[data-city='step_map_city${index+1}']")
                        .select("span.num")
                        .text()
                }
                // Log.i("distancing", "$result")
                return@async result
            }
            deffered.await().let {
                for (location in it) {
                    var color = Color.GRAY

                    when (location.value) {
                        "1" -> { color = R.color.distancing_lv1 }
                        "2" -> { color = R.color.distancing_lv2 }
                        "3" -> { color = R.color.distancing_lv3 }
                        "4" -> { color = R.color.distancing_lv4 }
                    }

                    mapEditColor(location.key, color)
                }
            }
        }
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}