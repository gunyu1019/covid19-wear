package kr.yhs.covid.activity

import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import kotlinx.coroutines.*
import kr.yhs.covid.R
import kr.yhs.covid.adapter.vaccinatedMenu.MenuAdapter
import kr.yhs.covid.adapter.vaccinatedMenu.MenuData
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class VaccinatedBoxActivity: MainResourceActivity(), CoroutineScope {
    lateinit var recyclerView: WearableRecyclerView

    private fun locationData(recyclerView: RecyclerView, selectionType: String) {
        launch {
            val deffered2 = async(Dispatchers.Default) {
                val response = Jsoup.connect(
                    "https://nip.kdca.go.kr/irgd/cov19stats.do?list=sido"
                ).parser(Parser.xmlParser()).get()

                val items = response.body().select("item")
                val result: ArrayList<MenuData> = arrayListOf()
                for (item in items) {
                    result.add(MenuData(
                        name = item.select("sidoNm").text(),
                        current = item.select("${selectionType}Cnt").text().toInt(),
                        total = item.select("${selectionType}Tot").text().toInt(),
                    ))
                }
                return@async result
            }
            deffered2.await().let {
                recyclerView.adapter = MenuAdapter(this@VaccinatedBoxActivity.context, it)
            }
        }
    }

    override fun onCreate() {
        mJob = Job()

        val firstTextView = view.findViewById<TextView>(R.id.first_TotalVaccinatedTextView)
        val firstCurrentTextView = view.findViewById<TextView>(R.id.first_currentVaccinatedTextView)
        val secondTextView = view.findViewById<TextView>(R.id.second_TotalVaccinatedTextView)
        val secondCurrentTextView = view.findViewById<TextView>(R.id.second_currentVaccinatedTextView)

        recyclerView = view.findViewById(R.id.vaccinated_menu_view)

        slideDown(recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = WearableLinearLayoutManager(context)
        recyclerView.isEdgeItemsCenteringEnabled = true
        recyclerView.adapter = MenuAdapter(context)

        val setLayoutList: MutableMap<String, ConstraintLayout> = mutableMapOf(
            "first" to view.findViewById(R.id.vaccinatedLayout1),
            "second" to view.findViewById(R.id.vaccinatedLayout2)
        )
        for (layout in setLayoutList.keys) {
            setLayoutList[layout]?.setOnClickListener {
                this@VaccinatedBoxActivity.slideUp(recyclerView)
                this.locationData(recyclerView, layout)
            }
        }

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

    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            view.height.toFloat(),
            0F,
        )
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,
            0F,
            0F,
            view.height.toFloat()
        )
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
}