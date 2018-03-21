package id.aasumitro.easynote.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import id.aasumitro.easynote.R
import id.aasumitro.easynote.ui.main.fragment.main.FragmentNoteList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //EZNote.mAppComp.inject(this)
        savedInstanceState.let { replaceFragment(FragmentNoteList()) }
    }

    fun replaceFragment (fragment: Fragment, cleanStack: Boolean = false) {
        if (cleanStack) clearBackStack()
        val ft = supportFragmentManager.beginTransaction()
        ft.apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun getToolbar(): Toolbar = this.toolbar

}
