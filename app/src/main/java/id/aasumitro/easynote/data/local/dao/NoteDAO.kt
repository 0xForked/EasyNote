package id.aasumitro.easynote.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import id.aasumitro.easynote.data.local.model.Notes
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CAT
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CONTENT
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_LOCK
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_TITLE
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_UPDATED
import io.reactivex.Flowable
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
@Dao
interface NoteDAO {

    @Insert
    fun postNotes(notes: Notes)

    @Query("SELECT * FROM notes where is_trashed = :isTrash")
    fun getAllNotes(isTrash: Boolean): Flowable<List<Notes>>

    @Query("SELECT * FROM notes WHERE category = :cat")
    fun getNotesByCategory(cat: String): Flowable<List<Notes>>

    @Query("SELECT * FROM notes WHERE title LIKE :title")
    fun getNoteByTitle(title: String): Flowable<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flowable<Notes>

    @Query("UPDATE notes SET " +
            "$DATABASE_TABLE_COLUMN_TITLE = :title, " +
            "$DATABASE_TABLE_COLUMN_CONTENT = :content, " +
            "$DATABASE_TABLE_COLUMN_LOCK = :isLocked, " +
            "$DATABASE_TABLE_COLUMN_CAT = :category, " +
            "$DATABASE_TABLE_COLUMN_UPDATED = :updatedAt " +
            "WHERE id = :id")
    fun putNotes(id: Long, title: String, content: String,
                 isLocked: Boolean, category: String, updatedAt: Date)

    @Query("UPDATE notes SET $DATABASE_TABLE_COLUMN_CAT = :defaultCategory " +
            "WHERE $DATABASE_TABLE_COLUMN_CAT = :deleteCategory" )
    fun updateCategory(defaultCategory: String, deleteCategory: String)

    @Query("UPDATE notes SET is_trashed = :isTrash WHERE id = :id")
    fun deleteNoteById(id: Long, isTrash: Boolean)

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteNotePermanentlyById(id: Long)

    @Query("DELETE FROM notes WHERE is_trashed = :isTrash")
    fun clearTrash(isTrash: Boolean)

}