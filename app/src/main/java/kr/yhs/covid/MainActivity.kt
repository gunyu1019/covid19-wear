package kr.yhs.covid

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.devs.vectorchildfinder.VectorChildFinder
import kr.yhs.covid.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = VectorChildFinder(this, R.drawable.map, binding.imageView)

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
            path.fillColor = Color.RED
        }
        
        binding.imageView.invalidate()
    }
}