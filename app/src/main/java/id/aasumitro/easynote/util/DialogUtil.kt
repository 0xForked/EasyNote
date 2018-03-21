package id.aasumitro.easynote.util

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import jp.wasabeef.richeditor.RichEditor
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat

/**
 * Created by Agus Adhi Sumitro on 21/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class DialogUtil {

    private var colorSelected: String? = null

    fun addLinkURLDialog(context: Context, mEditor: RichEditor) {
        val alertDialog = AlertDialog.Builder(context)
                .setIcon(CommonUtils.getAppIcon(context))
                .setTitle(context.resources.getString(R.string.text_insert_link))
                .setPositiveButton(context.resources.getString(R.string.text_insert), null)
                .create()
        val mInput = EditText(context)
        mInput.apply {
            hint = "url://"
            filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
                source.toString().filterNot { it.isWhitespace() }
            })
            inputType = InputType.TYPE_TEXT_VARIATION_URI
            imeOptions = EditorInfo.IME_ACTION_DONE
        }
        alertDialog.setOnShowListener{
            val mInsertLink = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            mInsertLink.setOnClickListener{
                val mLink = mInput.text.toString().trim()
                if (mLink.isEmpty()) mInput.error = context.resources.getString(R.string.error_password_input)
                if (!mLink.isEmpty()) {
                    mEditor.insertLink(mLink, mLink)
                    alertDialog.dismiss()
                    context.applicationContext.toast(context.resources.getString(R.string.text_insert_link))
                }
            }
        }
        alertDialog.setView(mInput,
                40, 40,
                40, 0)
        alertDialog.show()
    }

    fun createCategoryDialog(context: Context) {
        context.alert(Appcompat) {
            title = context.getString(R.string.text_create_category)
            icon = CommonUtils.getAppIcon(context)!!
            customView {
                relativeLayout {
                    padding = dip(20)
                    colorSelected = "#7e7e7e"
                    val edName = editText {
                        id = View.generateViewId()
                        hint = "Name"
                        topPadding = dip(15)
                        bottomPadding = dip(15)
                        inputType = InputType.TYPE_CLASS_TEXT
                        imeOptions = EditorInfo.IME_ACTION_NEXT
                    }.lparams(width = matchParent , height = wrapContent)
                    val edDesc = editText {
                        id = View.generateViewId()
                        hint = "Description"
                        topPadding = dip(15)
                        bottomPadding = dip(15)
                        inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                        imeOptions = EditorInfo.IME_ACTION_DONE
                    }.lparams(width = matchParent , height = wrapContent) { below(edName) }
                    val colorView = textView {
                        text = colorSelected
                        textSize = 25F
                        setTextColor(Color.parseColor(colorSelected))
                    }.lparams {
                        below(edDesc)
                        topMargin = dip(30)
                        leftMargin = dip(65)
                    }
                    relativeLayout {
                        imageView { setBackgroundResource(R.drawable.ic_color_lens_white_24dp) }.lparams{
                            centerHorizontally(); centerVertically() }
                        id = View.generateViewId()
                        setBackgroundColor(Color.parseColor(colorSelected))
                    }.lparams(width = dip(50),
                            height = dip(50)) {
                        topMargin = dip(10)
                        leftMargin = dip(10)
                        below(edDesc)
                    }.setOnClickListener {
                        context.alert {
                            customView{
                                relativeLayout {
                                    val layOne = linearLayout {
                                        id = View.generateViewId()
                                        checkBox { val clr = "#607D8B"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#E91E63"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#9C27B0"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#673AB7"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                     }.lparams { centerHorizontally(); topMargin = dip(10) }
                                    val layTwo = linearLayout {
                                        id = View.generateViewId()
                                        checkBox { val clr = "#3F51B5"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#2196F3"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#009688"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#8BC34A"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                    }.lparams { below(layOne); centerHorizontally(); topMargin = dip(10) }
                                    linearLayout {
                                        id = View.generateViewId()
                                        checkBox { val clr = "#CDDC39"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#FF9800"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#FF5722"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }
                                        }
                                        checkBox { val clr = "#795548"
                                            text = clr
                                            setTextColor(Color.parseColor(clr))
                                            setOnClickListener { colorSelected = clr }

                                        }
                                     }.lparams { below(layTwo); centerHorizontally(); topMargin = dip(10) }
                                }
                                positiveButton("set color") {
                                    colorView.text = colorSelected
                                    colorView.setTextColor(Color.parseColor(colorSelected))
                                }
                            }
                         }.show()
                    }
                    textView {
                        text = resources.getString(R.string.msg_cat_color)
                        textSize = 18F
                    }.lparams {
                        below(edDesc)
                        topMargin = dip(8)
                        leftMargin = dip(65)
                    }
                    positiveButton(resources.getString(R.string.text_created)) {
                        val getName = edName.text.toString()
                        val getDesc = edDesc.text.toString()
                        val getColor = colorView.text.toString()
                        if (getName.isEmpty()) context.toast("Name required"); context.toast("Failed created new category")
                        if (!getName.isEmpty()) { LocalDataSource.createNewCategory(getName, getDesc, getColor) }
                    }
                    negativeButton(resources.getString(R.string.text_cancel)) { }
                    neutralPressed(resources.getString(R.string.text_help)) { }
                }
            }
        }.show().setCancelable(false)
    }

}