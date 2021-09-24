package kr.yhs.covid.activity

import android.widget.TextView
import kotlinx.coroutines.*
import kr.yhs.covid.R
import org.jsoup.Jsoup
import kotlin.coroutines.CoroutineContext

class DistancingActivity: MainResourceActivity(), CoroutineScope {
    lateinit var textViews: ArrayList<TextView>

    override fun onCreate() {
        mJob = Job()
        textViews = arrayListOf(
            view.findViewById(R.id.lv4_location),
            view.findViewById(R.id.lv3_location),
            view.findViewById(R.id.lv2_location),
            view.findViewById(R.id.lv1_location)
        )

        launch {
            val deffered = async(Dispatchers.Default) {
                val response = Jsoup.connect(
                    "http://ncov.mohw.go.kr/regSocdisBoardView.do?brdId=6&brdGubun=68&ncvContSeq=495"
                ).get()

                val stepMapAll = response.body().select("div#stepMapAll")
                var warnings = stepMapAll.select("p.rssd_descript")

                val result = ArrayList<String>()
                for (index in 0..3) {
                    result.add(
                        warnings.first().html().replace("<br>", "\n")
                    )
                    if (index < 3)
                        warnings = warnings.nextAll("p.rssd_descript")
                }
                return@async result
            }
            deffered.await().let {
                for ((index, data) in it.withIndex()) {
                    textViews[index].text = data
                }
            }
        }
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}