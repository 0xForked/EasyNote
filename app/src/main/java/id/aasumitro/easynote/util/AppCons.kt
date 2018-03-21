package id.aasumitro.easynote.util


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
object AppCons {

    //Prefs constant
    const val APP_FIRST_LAUNCH  = "FirstLaunch"
    const val USER_PASSWORD     = "UserPassword"

    //Database cons
    const val DATABASE_NAME                 = "EZNote"
    //Database table cons
    const val DATABASE_TABLE_NOTE           = "notes"
    const val DATABASE_TABLE_CATEGORY       = "category"
    //Database table column cons
    const val DATABASE_TABLE_COLUMN_TITLE   = "title"
    const val DATABASE_TABLE_COLUMN_CONTENT = "content"
    const val DATABASE_TABLE_COLUMN_LOCK    = "is_locked"
    const val DATABASE_TABLE_COLUMN_TRASH   = "is_trashed"
    const val DATABASE_TABLE_COLUMN_CAT     = "category"
    const val DATABASE_TABLE_COLUMN_CREATED = "created_at"
    const val DATABASE_TABLE_COLUMN_UPDATED = "updated_at"
    const val DATABASE_TABLE_COLUMN_NAME    = "name"
    const val DATABASE_TABLE_COLUMN_DESC    = "description"
    const val DATABASE_TABLE_COLUMN_COLOR   = "color"

    //Detail activity Specific note type
    const val NEW_NOTE      = "new"
    const val DETAIL_NOTE   = "detail"

    //Intent const
    const val INTENT_DETAIL_STATUS      = "INTENT_STATUS"
    const val INTENT_DETAIL_NOTE_ID     = "INTENT_ID"

}