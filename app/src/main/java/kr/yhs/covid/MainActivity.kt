package kr.yhs.covid

import android.app.Activity
import android.os.Bundle
import android.support.wearable.input.RotaryEncoder
import android.view.MotionEvent
import kr.yhs.covid.activity.DashboardActivity
import kr.yhs.covid.activity.DistancingMapActivity
import kr.yhs.covid.databinding.ActivityMainBinding
import kr.yhs.covid.viewPage.ViewData
import kr.yhs.covid.viewPage.ViewPagerAdapter

class MainActivity : Activity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list: ArrayList<ViewData> = ArrayList()
        list.add(
            ViewData(
                R.layout.corona_total_page,
                activity = DashboardActivity(),
                context = this@MainActivity
            )
        )
        list.add(
            ViewData(
                R.layout.distancing_map_page,
                activity = DistancingMapActivity(),
                context = this@MainActivity
            )
        )
        list.add(
            ViewData(
                R.layout.distancing_page,
                context = this@MainActivity
            )
        )

        binding.viewPager.adapter = ViewPagerAdapter(list)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(event)) {
            val delta = -RotaryEncoder.getRotaryAxisValue(event)
            if (delta > 0 && binding.viewPager.currentItem <= binding.viewPager.adapter!!.itemCount)
                binding.viewPager.currentItem += 1
            else if (delta < 0 && binding.viewPager.currentItem >= 0)
                binding.viewPager.currentItem -= 1
        }
        return super.onGenericMotionEvent(event)
    }
}
