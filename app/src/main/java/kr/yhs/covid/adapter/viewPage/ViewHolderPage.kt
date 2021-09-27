package kr.yhs.covid.adapter.viewPage

import android.view.View
import android.view.ViewStub
import androidx.recyclerview.widget.RecyclerView
import kr.yhs.covid.R

class ViewHolderPage(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var viewStub: ViewStub = itemView.findViewById(R.id.page_viewer_include)

    fun onBind(data: ViewData): View {
        bindingAdapter
        viewStub.layoutResource = data.id
        return viewStub.inflate()
    }
}