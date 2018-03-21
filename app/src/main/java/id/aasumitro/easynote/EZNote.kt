package id.aasumitro.easynote

import android.app.Application
import id.aasumitro.easynote.data.local.room.CreatorDB
import id.aasumitro.easynote.di.AppComp
import id.aasumitro.easynote.di.AppModule
import id.aasumitro.easynote.di.DaggerAppComp

/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class EZNote : Application() {

    companion object { lateinit var mAppComp: AppComp }

    override fun onCreate() {
        super.onCreate()

        //Create database
        CreatorDB.createDb(this)

        //Init DI Component
        mAppComp = DaggerAppComp
                .builder()
                .appModule(AppModule(this))
                .build()

    }

}