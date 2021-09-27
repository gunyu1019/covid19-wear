package kr.yhs.covid

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.InputDeviceCompat
import androidx.core.view.ViewConfigurationCompat
import kr.yhs.covid.activity.DashboardActivity
import kr.yhs.covid.activity.DistancingActivity
import kr.yhs.covid.activity.DistancingMapActivity
import kr.yhs.covid.activity.VaccinatedBoxActivity
import kr.yhs.covid.databinding.ActivityMainBinding
import kr.yhs.covid.adapter.viewPage.ViewData
import kr.yhs.covid.adapter.viewPage.ViewPagerAdapter

class MainActivity : Activity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    var page: ArrayList<ViewData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        page.add(
            ViewData(
                R.layout.dashboard_page,
                activity = DashboardActivity(),
                context = this@MainActivity
            )
        )
        page.add(
            ViewData(
                R.layout.vaccinated,
                activity = VaccinatedBoxActivity(),
                context = this@MainActivity
            )
        )
        page.add(
            ViewData(
                R.layout.distancing_map_page,
                activity = DistancingMapActivity(),
                context = this@MainActivity
            )
        )
        page.add(
            ViewData(
                R.layout.distancing_page,
                activity = DistancingActivity(),
                context = this@MainActivity
            )
        )

        binding.viewPager.adapter = ViewPagerAdapter(page)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && event.isFromSource(InputDeviceCompat.SOURCE_ROTARY_ENCODER)) {
            val delta = event.getAxisValue(MotionEvent.AXIS_SCROLL) * ViewConfigurationCompat.getScaledVerticalScrollFactor(
                ViewConfiguration.get(this), this
            )
            if (delta > 0 && binding.viewPager.currentItem <= binding.viewPager.adapter!!.itemCount)
                binding.viewPager.currentItem += 1
            else if (delta < 0 && binding.viewPager.currentItem >= 0)
                binding.viewPager.currentItem -= 1
        }
        return super.onGenericMotionEvent(event)
    }

    override fun onBackPressed() {
        val nowPosition = page[binding.viewPager.currentItem]
        if (nowPosition.activity != null && nowPosition.id == R.layout.vaccinated) {
            val activity: VaccinatedBoxActivity = nowPosition.activity as VaccinatedBoxActivity
            if (activity.recyclerView.visibility != View.GONE) {
                activity.slideDown(activity.recyclerView)
                activity.recyclerView.visibility = View.GONE
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}
