package id.aasumitro.easynote.util

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import id.aasumitro.easynote.R


/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
object CommonUtils {

     fun getAppIcon(context: Context): Drawable? {
        val mBuildVersion = Build.VERSION.SDK_INT
        val mLollipop = Build.VERSION_CODES.LOLLIPOP
        //val mMarshmallow = Build.VERSION_CODES.M
        //val mNougat = Build.VERSION_CODES.N
        return when {
            mBuildVersion >= mLollipop -> {
                ContextCompat.getDrawable(
                        context.applicationContext,
                        R.drawable.ic_launcher_background)
            }
            else -> {
                @Suppress("DEPRECATION")
                context.resources
                        .getDrawable(R.drawable.ic_launcher_background)
            }
        }
    }

    fun openPlayStore(context: Context) {
        val appPackageName: String =  context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.app_market_link) + appPackageName)))
        } catch (e: android.content.ActivityNotFoundException){
            context.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.app_google_play_store_link) + appPackageName)))
        }
    }

}