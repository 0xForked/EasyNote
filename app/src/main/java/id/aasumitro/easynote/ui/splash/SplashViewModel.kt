package id.aasumitro.easynote.ui.splash

import android.arch.lifecycle.ViewModel
import id.aasumitro.easynote.util.AppCons.APP_FIRST_LAUNCH
import id.aasumitro.easynote.util.SharedPrefs
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class SplashViewModel : ViewModel() {

    private var mListener: SplashListener? = null
    private var mPrefsUtil: SharedPrefs? = null

    fun initViewModel(navigation: SplashListener,
                      prefsUtil: SharedPrefs) {
        this.mListener = navigation
        this.mPrefsUtil = prefsUtil
    }

    fun startTask() {
        Observable.interval(3, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe { onNextActivity() }
    }

    private fun isFirstLaunch() = mPrefsUtil!!.getBoolean(APP_FIRST_LAUNCH, true)

    private fun onNextActivity() {
        if (isFirstLaunch())
            mListener!!.startIntro()
        else
            mListener!!.startMain()
    }

}