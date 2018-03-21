package id.aasumitro.easynote.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import id.aasumitro.easynote.EZNote
import id.aasumitro.easynote.util.SharedPrefs
import javax.inject.Singleton


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

@Module
class AppModule (private val noteApp : EZNote) {

    @Provides
    @Singleton
    fun provideApplication() = noteApp

    @Provides
    @Singleton
    fun providePreferencesUtil(sharedPreferences: SharedPreferences): SharedPrefs =
            SharedPrefs(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
            provideApplication().getSharedPreferences("", Context.MODE_PRIVATE)

}