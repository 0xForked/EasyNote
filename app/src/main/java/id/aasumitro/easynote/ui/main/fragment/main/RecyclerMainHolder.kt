package id.aasumitro.easynote.ui.main.fragment.main

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import id.aasumitro.easynote.data.local.model.Join
import kotlinx.android.synthetic.main.item_note_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class RecyclerMainHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var updatedAt: String? = null
    private var categoryName: String? = null
    private var categoryColor: String? = null

    fun bindJoinTable(join: Join, listener: RecyclerMainListener) {

        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val fullDateUpdated = dateFormat.parse(join.updatedAtNote.toString())
        val previousCal = Calendar.getInstance()
        previousCal.time = fullDateUpdated
        val currentCal = Calendar.getInstance()
        val difference = currentCal.timeInMillis - previousCal.timeInMillis
        val second = TimeUnit.MILLISECONDS.toSeconds(difference)
        val minute = second / 60
        val hour = second / 3600
        val day = second / 86400
        updatedAt = when {
            second <= 60 -> "Updated at a few seconds ago"
            second in 60..120 -> "Updated at 1 minute ago"
            second in 120..3600 -> "Updated at $minute minutes ago"
            second in 3600..86400 -> "Last updated at $hour hours ago"
            second in 86400..2628000 -> "Last updated at $day  days ago"
            else -> "Last updated more than a month ago"
        }

        if (join.isLockedNote == true) { itemView.lockStatus.visibility = View.VISIBLE }
        categoryName = if (join.nameCategory == "Category") "none" else join.nameCategory
        categoryColor = if(join.colorCategory == "none") "#696969" else join.colorCategory

        itemView.notesTitle.text = join.titleNote
        itemView.noteUpdate.text = updatedAt
        itemView.noteCategory.setBackgroundColor(Color.parseColor(categoryColor))
        itemView.itemListClick.setOnClickListener{ listener.onItemClick(join) }

    }

}