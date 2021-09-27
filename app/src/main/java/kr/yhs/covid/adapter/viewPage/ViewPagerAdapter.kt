package kr.yhs.covid.adapter.viewPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.yhs.covid.R

class ViewPagerAdapter(private var data: ArrayList<ViewData>): RecyclerView.Adapter<ViewHolderPage>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPage {
        val context: Context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.page_viewer, parent, false)
        return ViewHolderPage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        val view = holder.onBind(data[position])
        data[position].view = view
        val activity = data[position].activity
        if (activity != null) {
            activity.loadData(data[position])
            activity.onCreate()
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}