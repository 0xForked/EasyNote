package id.aasumitro.easynote.util

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import id.aasumitro.easynote.R
import org.jetbrains.anko.toast


/**
 * Created by Agus Adhi Sumitro on 20/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class PasswordHelper {

    fun createPasswordDialog(context: Context, mPrefs: SharedPrefs) {
        val password = mPrefs.getString(AppCons.USER_PASSWORD, "UserPassword")
        if (password == "UserPassword") {
            val alertDialog = AlertDialog.Builder(context)
                    .setIcon(CommonUtils.getAppIcon(context))
                    .setTitle(context.resources.getString(R.string.text_set_password))
                    .setPositiveButton(context.resources.getString(R.string.text_save), null)
                    .setNegativeButton(context.resources.getString(R.string.text_later), null)
                    .setNeutralButton(context.resources.getString(R.string.text_show_password), null)
                    .create()
            val mLayout = LinearLayout(context)
            mLayout.orientation = LinearLayout.VERTICAL
            val mInput = EditText(context)
            mInput.apply {
                hint = "Password"
                filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                    source.toString().filterNot { it.isWhitespace() }
                })
                inputType = InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
                imeOptions = EditorInfo.IME_ACTION_DONE
            }
            mLayout.addView(mInput)
            alertDialog.setOnShowListener{
                val mShowPassword = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                mShowPassword.setOnClickListener {
                    @Suppress("DEPRECATED_IDENTITY_EQUALS")
                    if (mInput.inputType === InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        mInput.inputType = InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD
                        mShowPassword.text = context.resources.getString(R.string.text_show_password)
                    } else {
                        mInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        mShowPassword.text = context.resources.getString(R.string.text_hide_password)
                    }
                    mInput.setSelection(mInput.text.length)
                }
                val mLater = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                mLater.setOnClickListener { alertDialog.dismiss() }
                val mSavePassword = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                mSavePassword.setOnClickListener{
                    val mNewPwd = mInput.text.toString().trim()
                    if (mNewPwd.isEmpty()) mInput.error = context.resources.getString(R.string.error_password_input)
                    if (!mNewPwd.isEmpty()) {
                        mPrefs.putString(AppCons.USER_PASSWORD, mNewPwd)
                        alertDialog.dismiss()
                        context.applicationContext.toast(context.resources.getString(R.string.success_password_created))
                    }
                }
            }
            alertDialog.setView(mLayout,
                    40, 40,
                    40, 0)
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

}