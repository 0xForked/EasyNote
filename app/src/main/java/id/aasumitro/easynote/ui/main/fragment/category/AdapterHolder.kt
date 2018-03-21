package id.aasumitro.easynote.ui.main.fragment.category

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import id.aasumitro.easynote.data.local.model.Category
import kotlinx.android.synthetic.main.item_category_list.view.*


/**
 * Created by Agus Adhi Sumitro on 21/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class AdapterHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(category: Category, listener: RecyclerListener) {

        itemView.categoryName.text = category.name
        itemView.categoryColor.setBackgroundColor(Color.parseColor(category.color))
        itemView.categoryColor.setOnClickListener{ listener.onItemClick(category) }

    }

}