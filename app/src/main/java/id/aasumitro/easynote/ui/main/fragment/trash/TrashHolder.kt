package id.aasumitro.easynote.ui.main.fragment.trash

import android.support.v7.widget.RecyclerView
import android.view.View
import id.aasumitro.easynote.data.local.model.Notes
import kotlinx.android.synthetic.main.item_note_list.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Agus Adhi Sumitro on 21/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class TrashHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var updatedAt: String? = null

    fun bind(notes: Notes) {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val fullDateUpdated = dateFormat.parse(notes.updatedAt.toString())
        val previousCal = Calendar.getInstance()
        previousCal.time = fullDateUpdated
        val currentCal = Calendar.getInstance()
        val difference = currentCal.timeInMillis - previousCal.timeInMillis
        val second = TimeUnit.MILLISECONDS.toSeconds(difference)
        val minute = second / 60
        val hour = second / 3600
        val day = second / 86400
        updatedAt = when {
            second <= 60 -> "Deleted at a few seconds ago"
            second in 60..120 -> "Deleted at 1 minute ago"
            second in 120..3600 -> "Deleted at $minute minutes ago"
            second in 3600..86400 -> "Deleted at $hour hours ago"
            second in 86400..2628000 -> "Deleted at $day  days ago"
            else -> "Deleted at more than a month ago"
        }

        itemView.notesTitle.text = notes.title
        itemView.noteUpdate.text = updatedAt
    }

}