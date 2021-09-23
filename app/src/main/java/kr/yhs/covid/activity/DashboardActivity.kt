package kr.yhs.covid.activity

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import kr.yhs.covid.R
import org.jsoup.Jsoup
import kotlin.coroutines.CoroutineContext

class DashboardActivity: MainResourceActivity(), CoroutineScope {
    override fun onCreate() {
        mJob = Job()
        val total = view.findViewById<TextView>(R.id.totalTextView)
        val totalAddition = view.findViewById<TextView>(R.id.totalAdditionTextView)
        val clinic = view.findViewById<TextView>(R.id.clinicTextView)
        val cured = view.findViewById<TextView>(R.id.curedTextView)
        val death = view.findViewById<TextView>(R.id.deathTextView)

        total.text = context.getString(R.string.people, "0")
        totalAddition.text = context.getString(R.string.people_addition, "0")
        clinic.text = context.getString(R.string.people, "0")
        cured.text = context.getString(R.string.people, "0")
        death.text = context.getString(R.string.people, "0")

        launch {
            val deffered = async(Dispatchers.Default) {
                val response = Jsoup.connect(
                    "http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=11"
                ).get()

                val case = response
                    .body()
                    .select("div.caseTable")
                    .select("div")

                val result = arrayListOf<String>()
                val firstData = case.first()
                result.add(
                    firstData.select("dd.ca_value").first().text()
                )
                result.add(
                    firstData.select("p.inner_value").first().text()
                )

                var data = case
                for (index in 0..2) {
                    data = data.nextAll()
                    result.add(
                        data.select("dd.ca_value").first().text()
                    )
                }

                return@async result
            }
            deffered.await().let {
                total.text = context.getString(R.string.people, it[0])
                totalAddition.text = context.getString(R.string.people_addition, it[1])
                clinic.text = context.getString(R.string.people, it[2])
                cured.text = context.getString(R.string.people, it[3])
                death.text = context.getString(R.string.people, it[4])
            }
        }
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}