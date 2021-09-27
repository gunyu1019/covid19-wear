package kr.yhs.covid.adapter.vaccinatedMenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.yhs.covid.R

class MenuAdapter(private var data: ArrayList<MenuData>): RecyclerView.Adapter<MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val context: Context = parent.context
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.page_viewer, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}