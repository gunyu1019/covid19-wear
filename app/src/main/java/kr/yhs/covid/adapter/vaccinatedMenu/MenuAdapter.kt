package kr.yhs.covid.adapter.vaccinatedMenu

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.yhs.covid.R

class MenuAdapter(val context: Activity, var data: ArrayList<MenuData>? = null): RecyclerView.Adapter<MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val context: Context = parent.context
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.vaccinated_menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        if (this.data == null)
            return
        holder.onBind(this.data!![position], context)
    }

    override fun getItemCount(): Int {
        if (this.data == null)
            return 0
        return this.data!!.size
    }
}