package id.aasumitro.easynote.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import id.aasumitro.easynote.data.local.model.Category
import io.reactivex.Flowable


/**
 * Created by Agus Adhi Sumitro on 18/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Dao
interface CategoryDAO {

    @Insert
    fun postCategory(category: Category)

    @Query("SELECT * FROM category WHERE id NOT IN(:id)")
    fun getCategoryList(id: Long): Flowable<List<Category>>

    @Query("DELETE FROM category WHERE id = :id")
    fun deleteCategoryById(id: Long)

}