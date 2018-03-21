package id.aasumitro.easynote.data.local.room

import android.arch.persistence.room.Room
import android.content.Context
import id.aasumitro.easynote.util.AppCons.DATABASE_NAME


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
object CreatorDB {

    lateinit var mDatabase: AppDB

    fun createDb(context: Context): AppDB {

        mDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()

        return mDatabase

    }

}