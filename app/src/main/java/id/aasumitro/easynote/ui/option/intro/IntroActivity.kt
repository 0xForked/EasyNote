package id.aasumitro.easynote.ui.option.intro

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import id.aasumitro.easynote.EZNote
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.util.AppCons.APP_FIRST_LAUNCH
import id.aasumitro.easynote.util.PasswordHelper
import id.aasumitro.easynote.util.SharedPrefs
import kotlinx.android.synthetic.main.activity_intro.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class IntroActivity : AppCompatActivity() {

    @Inject
    lateinit var mPrefs: SharedPrefs
    private var mLayouts: IntArray? = null

    init {
        mLayouts = intArrayOf(
                R.layout.item_slide_one,
                R.layout.item_slide_two,
                R.layout.item_slide_three,
                R.layout.item_slide_four)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(R.layout.activity_intro)
        EZNote.mAppComp.inject(this)
        addBottomDots(0)
        changeStatusBarColor()
        PasswordHelper().createPasswordDialog(this, mPrefs)
        val mViewPagerAdapter = ViewPagerAdapter()
        introViewPager.apply {
            adapter = mViewPagerAdapter
            addOnPageChangeListener(viewPagerPageChangeListener)
        }
        introButtonSkip.setOnClickListener { startMain() }
        introButtonNext.setOnClickListener {
            val current = getItem(+1)
            if (current < mLayouts!!.size)
                introViewPager.currentItem = current
            else
                startMain()
        }
        LocalDataSource.createNewCategory("Category", "none", "none")
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(mLayouts!!.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        introLayoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.apply {
                text = fromHtml("&#8226;")
                textSize = 40f
                setTextColor(colorsInactive[currentPage])
            }
            introLayoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(source)
        }
    }

    private fun getItem(i: Int): Int = introViewPager.currentItem + i

    private fun startMain() {
        mPrefs.putBoolean(APP_FIRST_LAUNCH, false)
        startActivity<MainActivity>()
        finish()
    }

    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
            object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position == mLayouts!!.size - 1) {
                introButtonNext.text = getString(R.string.text_start)
                introButtonSkip.visibility = View.GONE
            } else {
                introButtonNext.text = getString(R.string.text_next)
                introButtonSkip.visibility = View.VISIBLE
            }
        }
        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) { }
        override fun onPageScrollStateChanged(arg0: Int) { }
    }

    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    inner class ViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater!!.inflate(mLayouts!![position], container, false)
            container.addView(view)
            return view
        }
        override fun getCount(): Int = mLayouts!!.size
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

}
