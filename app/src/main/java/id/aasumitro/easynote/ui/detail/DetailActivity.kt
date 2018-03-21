package id.aasumitro.easynote.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import id.aasumitro.easynote.EZNote
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.GlobalTask
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Category
import id.aasumitro.easynote.util.AppCons.DETAIL_NOTE
import id.aasumitro.easynote.util.AppCons.INTENT_DETAIL_NOTE_ID
import id.aasumitro.easynote.util.AppCons.INTENT_DETAIL_STATUS
import id.aasumitro.easynote.util.AppCons.NEW_NOTE
import id.aasumitro.easynote.util.DialogUtil
import id.aasumitro.easynote.util.PasswordHelper
import id.aasumitro.easynote.util.SharedPrefs
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var mPrefs: SharedPrefs
    private var mCategoryName: String? = null
    private var mDetailStatus: String? = null
    private var mNoteIdFromIntent: String? = null
    private var mNoteTitleFromDB: String? = null
    private var mNoteContentFromDB: String? = null
    private var mNoteLockFromDB: String? = null
    private var mNoteCreateFromDB: Date? = null
    private var mNoteUpdatedFromDB: Date? = null

    private val mViewModel: DetailViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        EZNote.mAppComp.inject(this)
        initIntentData()
        initDetailEditor()
        mViewModel.getCategoryList()
    }

    private fun initIntentData() {
        val mIntentStatus = intent.getStringExtra(INTENT_DETAIL_STATUS)
        val mIntentNoteID = intent.getStringExtra(INTENT_DETAIL_NOTE_ID)
        if (mIntentStatus == null) {
            throw IllegalArgumentException(getString(R.string.error_no_extra_key))
        } else {
            mDetailStatus = mIntentStatus
            if (mIntentNoteID != null &&
                    mDetailStatus == DETAIL_NOTE) {
                mNoteIdFromIntent = mIntentNoteID
                onNoteEdited(mNoteIdFromIntent!!)
            }
        }
    }

    private fun initDetailEditor() {
        val mEditor = etDetailDesc as RichEditor
        mEditor.apply {
            setEditorHeight(200)
            setEditorFontSize(20)
            setEditorFontColor(Color.BLACK)
            setPlaceholder(getString(R.string.msg_insert_text))
        }
        mEditor.setOnTextChangeListener { text -> textDetail.text = text }
        actionUnlock.setOnClickListener {
            PasswordHelper().createPasswordDialog(this, mPrefs)
            actionLock.visibility = View.VISIBLE
            actionUnlock.visibility = View.GONE
            textLock.text = true.toString()
            toast("Note lock : ${textLock.text}")
        }
        actionLock.setOnClickListener {
            actionLock.visibility = View.GONE
            actionUnlock.visibility = View.VISIBLE
            textLock.text = false.toString()
            toast("Note lock : ${textLock.text}")
        }
        actionUndo.setOnClickListener { mEditor.undo() }
        actionRedo.setOnClickListener { mEditor.redo() }
        actionBold.setOnClickListener { mEditor.setBold() }
        actionItalic.setOnClickListener { mEditor.setItalic() }
        actionUnderline.setOnClickListener { mEditor.setUnderline() }
        actionStrikeThrough.setOnClickListener { mEditor.setStrikeThrough() }
        actionAlignLeft.setOnClickListener { mEditor.setAlignLeft() }
        actionAlignCenter.setOnClickListener { mEditor.setAlignCenter() }
        actionAlignRight.setOnClickListener { mEditor.setAlignRight() }
        actionInsertLink.setOnClickListener { DialogUtil().addLinkURLDialog(this, mEditor)}
        if (mDetailStatus == DETAIL_NOTE) {
            actionDelete.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    alert {
                        title = getString(R.string.msg_sure_question)
                        message = getString(R.string.msg_delete_note)
                        positiveButton(getString(R.string.text_delete)) {
                           LocalDataSource.updateIsTrashed(mNoteIdFromIntent!!.toLong(), true)
                            toast("${etDetailTitle.text} "+getString(R.string.msg_moved_trash))
                            finish()
                        }
                        negativeButton(getString(R.string.text_cancel)) { }
                        neutralPressed(getString(R.string.text_permanent)) {
                            LocalDataSource.deleteNotePermanentById(mNoteIdFromIntent!!.toLong())
                            toast("${etDetailTitle.text} "+getString(R.string.msg_delete_permanent))
                            finish()
                        }
                    }.show()
                }
            }
            actionInfo.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    alert {
                        title = "$mNoteTitleFromDB ${getString(R.string.text_information)}"
                        message = "${getString(R.string.text_created_at)} $mNoteCreateFromDB \n" +
                                  "${getString(R.string.text_updated_at) } $mNoteUpdatedFromDB \n" +
                                  "${getString(R.string.text_lock_status)}  $mNoteLockFromDB"
                        okButton {  }
                    }.show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val item = menu!!.findItem(R.id.menu_category)
        val spinner = item.actionView as Spinner
        spinner.tag = "Category"
        val listFromDB = GlobalTask.mCategoryList
        val listCategory = ArrayList<String>()
        (0 until listFromDB.size).mapTo(listCategory) { listFromDB[it].name!! }
        val adapterName = ArrayAdapter<String>(this,
                R.layout.item_spinner_dropdown, listCategory)
        val adapterFull = ArrayAdapter<Category>(this,
                R.layout.item_spinner_dropdown, listFromDB)
        //val adapter = ArrayAdapter.createFromResource(this,
                //R.array.category_list, R.layout.item_spinner_dropdown)
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterName
        //spinner.setPopupBackgroundResource(R.color.white)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, pos: Int, id: Long) {
                val ctg = adapterFull.getItem(pos)
                //mCategoryName = spinner.selectedItem.toString()
                mCategoryName = ctg.name
                //val databaseId=Integer.parseInt((spinner.selectedItem).getId())
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
        if (mDetailStatus == DETAIL_NOTE) {
            spinner.setSelection(spinnerSelectedItem(spinner, mCategoryName!!))
        }

        return super.onCreateOptionsMenu(menu)

    }

    private fun spinnerSelectedItem(spinner: Spinner, category: String): Int {
        return (0 until spinner.count).firstOrNull {
            spinner.getItemAtPosition(it)
                    .toString()
                    .equals(category, ignoreCase=true)
        } ?: 0
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.menu_done -> {
                when(mDetailStatus) {
                    NEW_NOTE -> onActionCreated()
                    DETAIL_NOTE -> onActionUpdated()
                    else -> toast(getString(R.string.text_nothing))
                }
            }
            android.R.id.home -> {
                if (etDetailTitle.text.toString().isEmpty() and
                        textDetail.text.toString().isEmpty()) {
                    onBackPressed()
                } else {
                    when(mDetailStatus) {
                        NEW_NOTE -> {
                            alert {
                                title = getString(R.string.msg_sure_question)
                                message = getString(R.string.msg_lose_new_note)
                                positiveButton(getString(R.string.text_save) + " DATA") {
                                    onActionCreated()
                                    onBackPressed()
                                }
                                negativeButton(getString(R.string.text_start)) { onBackPressed() }
                            }.show()
                        }
                        DETAIL_NOTE -> {
                            val detailTitle = etDetailTitle.text.toString()
                            val detailContent = textDetail.text.toString()
                            if (detailTitle != mNoteTitleFromDB ||
                                    mNoteContentFromDB != detailContent) {
                                alert {
                                    title = getString(R.string.msg_sure_question)
                                    message = getString(R.string.msg_lost_change)
                                    positiveButton(getString(R.string.text_save) + " DATA") {
                                        onActionUpdated()
                                        onBackPressed()
                                    }
                                    negativeButton(getString(R.string.text_start)) { onBackPressed() }
                                }.show()
                            } else { onBackPressed() }
                        }
                        else -> onBackPressed()
                    }
                }
            }
        }
        return true
    }

    private fun onNoteEdited(id: String) {
        mViewModel.getDetailData(id.toLong())
        mViewModel.getTitle().observe(this, Observer {
            etDetailTitle.setText(it)
            mNoteTitleFromDB = it
        })
        mViewModel.getContent().observe(this, Observer {
            val mEditor = etDetailDesc as RichEditor
            mEditor.html = it
            textDetail.text = it
            mNoteContentFromDB = it
        })
        mViewModel.getLockStatus().observe(this, Observer {
            when(it) {
                true -> {
                    actionLock.visibility = View.VISIBLE
                    actionUnlock.visibility = View.GONE
                }
                else -> {
                    actionLock.visibility = View.GONE
                    actionUnlock.visibility = View.VISIBLE
                }
            }
            textLock.text = it.toString()
            mNoteLockFromDB = it.toString()
            //this.window.decorView.snack("Note is locked = $it") { }
            toast("Note is locked = $it")
        })
        mViewModel.getCategory().observe(this, Observer { mCategoryName = it })
        mViewModel.getDateCreated().observe(this, Observer { mNoteCreateFromDB = it })
        mViewModel.getDateUpdated().observe(this, Observer { mNoteUpdatedFromDB = it })
    }

    private fun onActionCreated() {
        val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
        val title = etDetailTitle.text.toString()
        val desc = textDetail.text.toString()
        val lock = textLock.text.toString()
        if (title.isEmpty()) etDetailTitle.error = resources.getString(R.string.error_require)
        if (desc.isEmpty()) cardDesc.startAnimation(shake)
        if (!desc.isEmpty() && !title.isEmpty()) {
            if (mCategoryName == "Category") {
                mCategoryName = "Category"
                toast(getString(R.string.msg_no_cat_selected))
                toast("Set to default - none")
            }
            mViewModel.postNewNotes(
                    title, desc,
                    lock.toBoolean(),
                    mCategoryName!!)
            finish()
        }
        hideKeyboard()
    }

    private fun onActionUpdated() {
        val detailTitle = etDetailTitle.text.toString()
        val detailContent = textDetail.text.toString()
        val detailLock = textLock.text.toString()
        if (detailTitle != mNoteTitleFromDB ||
                mNoteContentFromDB != detailContent ||
                    detailLock != mNoteLockFromDB) {
            if (mCategoryName == "Category") {
                mCategoryName = "Category"
                toast(getString(R.string.msg_no_cat_selected))
                toast("Set to default - none")
            }
            mViewModel.updateEditedNotes(
                    mNoteIdFromIntent!!.toLong(),
                    detailTitle,
                    detailContent,
                    detailLock.toBoolean(),
                    mCategoryName!!)
            finish()
        } else {
            toast(getString(R.string.msg_no_change))
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right)
    }

}
