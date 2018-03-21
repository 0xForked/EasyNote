package id.aasumitro.easynote.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.aasumitro.easynote.EZNote
import id.aasumitro.easynote.R
import id.aasumitro.easynote.ui.option.intro.IntroActivity
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.util.SharedPrefs
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), SplashListener {

    @Inject
    lateinit var mPrefsUtil: SharedPrefs

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        EZNote.mAppComp.inject(this)
        mViewModel.initViewModel(this, mPrefsUtil)
        mViewModel.startTask()
    }

    override fun startMain() {
        startActivity<MainActivity>()
        finish()
    }

    override fun startIntro() {
        startActivity<IntroActivity>()
        finish()
    }

}
