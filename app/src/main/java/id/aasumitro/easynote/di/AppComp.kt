package id.aasumitro.easynote.di

import dagger.Component
import id.aasumitro.easynote.ui.detail.DetailActivity
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.ui.option.intro.IntroActivity
import id.aasumitro.easynote.ui.splash.SplashActivity
import javax.inject.Singleton


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
@Singleton
@Component(modules = [
    (AppModule::class)
])

interface AppComp {

    fun inject(target: SplashActivity)

    fun inject(target: IntroActivity)

    fun inject(target: MainActivity)

    fun inject(target: DetailActivity)


}