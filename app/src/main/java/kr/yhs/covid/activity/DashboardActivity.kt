package kr.yhs.covid.activity

import android.widget.TextView
import kr.yhs.covid.R

class DashboardActivity: MainResourceActivity() {
    override fun onCreate() {
        val total = view.findViewById<TextView>(R.id.totalTextView)
        val totalAddition = view.findViewById<TextView>(R.id.totalAdditionTextView)
        val clinic = view.findViewById<TextView>(R.id.clinicTextView)
        val cured = view.findViewById<TextView>(R.id.curedTextView)
        val death = view.findViewById<TextView>(R.id.deathTextView)

        total.text = context.getString(R.string.people, 0)
        totalAddition.text = context.getString(R.string.people_addition, 0)
        clinic.text = context.getString(R.string.people, 0)
        cured.text = context.getString(R.string.people, 0)
        death.text = context.getString(R.string.people, 0)
    }
}