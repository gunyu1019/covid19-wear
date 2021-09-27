package kr.yhs.covid.adapter.vaccinatedMenu

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.yhs.covid.R
import java.text.NumberFormat
import java.util.*

class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var totalTextView = itemView.findViewById<TextView>(R.id.menu_TotalVaccinatedTextView)
    private var currentTextView = itemView.findViewById<TextView>(R.id.menu_currentVaccinatedTextView)
    private var locationName = itemView.findViewById<TextView>(R.id.menu_vaccinatedTextViewLocationName)

    private fun getNumberToString(number: Int): String =
        NumberFormat.getNumberInstance(Locale.US).format(number)

    fun onBind(data: MenuData, context: Activity) {
        totalTextView.text = context.getString(R.string.people, getNumberToString(data.total))
        currentTextView.text = context.getString(R.string.people_addition_template, getNumberToString(data.current))
        locationName.text = data.name
        bindingAdapter
    }
}