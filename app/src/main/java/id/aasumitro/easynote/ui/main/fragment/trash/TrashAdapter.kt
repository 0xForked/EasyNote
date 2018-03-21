package id.aasumitro.easynote.ui.main.fragment.trash

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Notes


/**
 * Created by Agus Adhi Sumitro on 21/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class TrashAdapter (private val noteList: ArrayList<Notes>) :
        RecyclerView.Adapter<TrashHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_list, parent, false)
        return TrashHolder(view)
    }

    override fun getItemCount(): Int = noteList.count()

    override fun onBindViewHolder(holder: TrashHolder, position: Int) =
            holder.bind(noteList[position])

    fun restoreBack(position: Int) {
        LocalDataSource.updateIsTrashed(noteList[position].id!!, false)
    }

    fun deleteTrashItem(position: Int) {
        position.let {
            LocalDataSource.deleteNotePermanentById(noteList[it].id!!.toLong())
            noteList.removeAt(it)
            notifyItemRemoved(it)
        }
    }

}