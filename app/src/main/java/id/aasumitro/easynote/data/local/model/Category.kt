package id.aasumitro.easynote.data.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_CATEGORY
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_COLOR
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_CREATED
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_DESC
import id.aasumitro.easynote.util.AppCons.DATABASE_TABLE_COLUMN_NAME
import id.aasumitro.easynote.util.DateConverter
import java.util.*

/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Entity(tableName = DATABASE_TABLE_CATEGORY)
data class Category(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_NAME)
        val name: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_DESC)
        var description: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_COLOR)
        var color: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_CREATED)
        @TypeConverters(DateConverter::class)
        var createdAt: Date?
)