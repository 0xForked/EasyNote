package id.aasumitro.easynote.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import id.aasumitro.easynote.data.local.dao.CategoryDAO
import id.aasumitro.easynote.data.local.dao.JoinTableDAO
import id.aasumitro.easynote.data.local.dao.NoteDAO
import id.aasumitro.easynote.data.local.model.Category
import id.aasumitro.easynote.data.local.model.Notes
import id.aasumitro.easynote.util.DateConverter


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
@Database(entities = [(Notes::class), (Category::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDB : RoomDatabase() {
    abstract fun notesDao(): NoteDAO
    abstract fun categoryDao(): CategoryDAO
    abstract fun joinDAO(): JoinTableDAO
}