package id.aasumitro.easynote.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import id.aasumitro.easynote.data.local.model.Join
import io.reactivex.Flowable


/**
 * Created by Agus Adhi Sumitro on 20/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
@Dao
interface JoinTableDAO {

    @Query("SELECT " +
            "notes.id, " +
            "notes.title, " +
            "notes.content, " +
            "notes.is_locked, " +
            "notes.is_trashed, " +
            "notes.created_at, " +
            "notes.updated_at, " +
            "category.name, " +
            "category.description," +
            "category.color " +
            "FROM notes, category " +
            "WHERE notes.category = category.name AND is_trashed = :isTrash")
    fun getJoinData(isTrash: Boolean): Flowable<List<Join>>

}

