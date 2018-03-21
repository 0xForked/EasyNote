package id.aasumitro.easynote.data.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CAT
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CONTENT
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CREATED
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_LOCK
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_TITLE
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_TRASH
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_UPDATED
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_NOTE
import id.aasumitro.easynote.util.DateConverter
import java.util.*

/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Entity(tableName = DATABASE_TABLE_NOTE)
data class Notes(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_TITLE)
        val title: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CONTENT)
        var content: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_LOCK)
        var isLocked: Boolean?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_TRASH)
        var isTrashed: Boolean?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CAT)
        var category: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CREATED)
        @TypeConverters(DateConverter::class)
        var createdAt: Date?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_UPDATED)
        @TypeConverters(DateConverter::class)
        var updatedAt: Date?
)