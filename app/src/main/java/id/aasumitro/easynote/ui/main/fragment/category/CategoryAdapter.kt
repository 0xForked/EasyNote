package id.aasumitro.easynote.ui.main.fragment.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.model.Category


/**
 * Created by Agus Adhi Sumitro on 21/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class CategoryAdapter (private val categoryList: ArrayList<Category>,
                       private val listener: RecyclerListener) :
        RecyclerView.Adapter<AdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_list, parent, false)
        return AdapterHolder(view)
    }

    override fun getItemCount(): Int = categoryList.count()

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) =
            holder.bind(categoryList[position], listener)

}