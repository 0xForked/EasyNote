package id.aasumitro.easynote.data.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.TypeConverters
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_COLOR
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CONTENT
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CREATED
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_DESC
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_LOCK
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_NAME
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_TITLE
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_TRASH
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_UPDATED
import id.aasumitro.easynote.util.DateConverter
import java.util.*
/**
 * Created by Agus Adhi Sumitro on 20/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
data class Join(
        @ColumnInfo(name = "id")
        var idNote: Long?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_TITLE)
        val titleNote: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CONTENT)
        var contentNote: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_LOCK)
        var isLockedNote: Boolean?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_TRASH)
        var isTrashedNote: Boolean?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CREATED)
        @TypeConverters(DateConverter::class)
        var createdAtNote: Date?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_UPDATED)
        @TypeConverters(DateConverter::class)
        var updatedAtNote: Date?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_NAME)
        var nameCategory: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_DESC)
        var descCategory: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_COLOR)
        var colorCategory: String?)