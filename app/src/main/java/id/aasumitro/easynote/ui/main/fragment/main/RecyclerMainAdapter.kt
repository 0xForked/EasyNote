package id.aasumitro.easynote.ui.main.fragment.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Join
import org.jetbrains.anko.design.snackbar


/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class RecyclerMainAdapter(private val joinTable: ArrayList<Join>,
                          private val listener: RecyclerMainListener) :
        RecyclerView.Adapter<RecyclerMainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMainHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_list, parent, false)
        return RecyclerMainHolder(view)
    }

    override fun getItemCount(): Int = joinTable.count()

    override fun onBindViewHolder(holder: RecyclerMainHolder, position: Int) =
            holder.bindJoinTable(joinTable[position], listener)

    fun removeAt(position: Int, view: View) {
        LocalDataSource.updateIsTrashed(
                joinTable[position].idNote!!.toLong(),
                true)
        view.let { snackbar(it,"${joinTable[position].titleNote} moved to trash") }
        joinTable.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}