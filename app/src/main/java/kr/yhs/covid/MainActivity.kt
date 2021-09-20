package kr.yhs.covid

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.devs.vectorchildfinder.VectorChildFinder
import kr.yhs.covid.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var map: VectorChildFinder

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

        binding.imageView.invalidate()
    }

        private fun mapEditColor(path: String, defaultColor: Int = Color.GRAY) {
        val pathD = map.findPathByName(path)
        pathD.fillColor = defaultColor
        binding.imageView.invalidate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        map = VectorChildFinder(this, R.drawable.korea_map, binding.imageView)

        mapCreate()

    }
}
