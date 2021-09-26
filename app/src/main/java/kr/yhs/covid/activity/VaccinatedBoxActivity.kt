package kr.yhs.covid.activity

import android.widget.TextView
import kotlinx.coroutines.*
import kr.yhs.covid.R
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class VaccinatedBoxActivity: MainResourceActivity(), CoroutineScope {
    override fun onCreate() {
        mJob = Job()

        val firstTextView = view.findViewById<TextView>(R.id.first_TotalVaccinatedTextView)
        val firstCurrentTextView = view.findViewById<TextView>(R.id.first_currentVaccinatedTextView)
        val secondTextView = view.findViewById<TextView>(R.id.second_TotalVaccinatedTextView)
        val secondCurrentTextView = view.findViewById<TextView>(R.id.second_currentVaccinatedTextView)

        launch {
            val deffered = async(Dispatchers.Default) {
                val response = Jsoup.connect(
                    "https://nip.kdca.go.kr/irgd/cov19stats.do?list=all"
                ).parser(Parser.xmlParser()).get()

                val items = response.body().select("item")
                val result: ArrayList<ArrayList<Int>> = arrayListOf()
                for (item in items) {
                    result.add(arrayListOf(
                        item.select("firstCnt").text().toInt(),
                        item.select("secondCnt").text().toInt()
                    ))
                }
                return@async result
            }
            deffered.await().let {
                firstTextView.text = context.getString(
                    R.string.people,
                    NumberFormat.getNumberInstance(Locale.US).format(it[2][0])
                )
                firstCurrentTextView.text = context.getString(
                    R.string.people_addition_template,
                    NumberFormat.getNumberInstance(Locale.US).format(it[0][0])
                )
                secondTextView.text = context.getString(
                    R.string.people,
                    NumberFormat.getNumberInstance(Locale.US).format(it[2][1])
                )
                secondCurrentTextView.text = context.getString(
                    R.string.people_addition_template,
                    NumberFormat.getNumberInstance(Locale.US).format(it[0][1])
                )
            }
        }
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}