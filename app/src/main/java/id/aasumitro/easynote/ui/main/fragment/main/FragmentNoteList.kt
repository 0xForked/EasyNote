package id.aasumitro.easynote.ui.main.fragment.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.GlobalTask
import id.aasumitro.easynote.data.local.model.Join
import id.aasumitro.easynote.ui.detail.DetailActivity
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.ui.main.fragment.category.FragmentCategoryList
import id.aasumitro.easynote.ui.main.fragment.trash.FragmentTrashList
import id.aasumitro.easynote.util.AppCons.DETAIL_NOTE
import id.aasumitro.easynote.util.AppCons.INTENT_DETAIL_NOTE_ID
import id.aasumitro.easynote.util.AppCons.INTENT_DETAIL_STATUS
import id.aasumitro.easynote.util.AppCons.NEW_NOTE
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class FragmentNoteList : Fragment(), MainListener, RecyclerMainListener, View.OnClickListener {

    private var isFabOpen: Boolean = false
    private var mCategoryName: String? = null
    private var mCategoryId: Int? = null

    init {
        mCategoryName = "none"
        mCategoryId = 1
    }

    private fun mainActivity() = activity as MainActivity
    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)
        itemsRecyclerView.let { initRecyclerView(view) }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        //EZNote.mAppComp.inject(this)
        mainActivity().getToolbar().apply {
            this.title = getString(R.string.app_name) + " list"
            this.navigationIcon = null
        }
        fab.setOnClickListener(this)
        fabCreate.setOnClickListener(this)
        fabCategory.setOnClickListener(this)
        fabTrash.setOnClickListener(this)
        mViewModel.initViewModel(this)
        mViewModel.getCategoryList()
        mViewModel.getListTask()
    }

    private fun initRecyclerView(view: View){
        view.itemsRecyclerView.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        view.itemsRecyclerView.layoutManager = layoutManager
        view.itemsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.itemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fab.isShown) fab.hide()
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) fab.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        view.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            mViewModel.getListTask()
        }
    }

    override fun initListAdapter() {
        val mAdapter = RecyclerMainAdapter(mViewModel.mJoinedList, this)
        itemsRecyclerView.adapter = mAdapter
        val swipeHandler = object : RecyclerMainSwipeDelete(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mAdapter.removeAt(viewHolder.adapterPosition, view!!)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView)
        //itemsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onItemClick(join: Join) {
        activity!!.startActivity<DetailActivity>(
                INTENT_DETAIL_STATUS to DETAIL_NOTE,
                INTENT_DETAIL_NOTE_ID to join.idNote.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val item = menu.findItem(R.id.menu_category)
        val spinner = item.actionView as Spinner
        val listFromDB = GlobalTask.mCategoryList
        val listCategory = ArrayList<String>()
        (0 until listFromDB.size).mapTo(listCategory) { listFromDB[it].name!! }
        val adapter = ArrayAdapter<String>(activity,
                R.layout.item_spinner_dropdown, listCategory)
        //val adapter = ArrayAdapter.createFromResource(activity,
                //R.array.category_list, R.layout.item_spinner_dropdown)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        //spinner.setPopupBackgroundResource(R.color.white)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, pos: Int, id: Long) {
                mCategoryName = spinner.selectedItem.toString()
                activity!!.toast("${spinner.selectedItem}")
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.filter_by_title ->  activity!!.toast("title")
            R.id.filter_by_created -> activity!!.toast("Created_at")
            R.id.filter_by_updated -> activity!!.toast("updated_at")
            R.id.menu_setting -> activity!!.toast("This is setting")
        }
        return true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> animateFAB()
            R.id.fabCreate -> {
                animateFAB()
                activity!!.startActivity<DetailActivity>(INTENT_DETAIL_STATUS to NEW_NOTE)
            }
            R.id.fabCategory -> {
                animateFAB()
                mainActivity().replaceFragment(FragmentCategoryList())
            }
            R.id.fabTrash -> {
                animateFAB()
                mainActivity().replaceFragment(FragmentTrashList())
            }
        }
    }

    private fun animateFAB() {
        val fabOpen = AnimationUtils.loadAnimation(activity, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(activity, R.anim.fab_close)
        val rotateForward = AnimationUtils.loadAnimation(activity,R.anim.rotate_forward)
        val rotateBackward = AnimationUtils.loadAnimation(activity, R.anim.rotate_backward)
        val inLeftToRight = AnimationUtils.loadAnimation(activity, R.anim.left_to_right)
        val inRightToLeft = AnimationUtils.loadAnimation(activity, R.anim.right_to_left)
        if (isFabOpen) {
            fab.startAnimation(rotateBackward)
            fabCreate.startAnimation(fabClose)
            fabCategory.startAnimation(fabClose)
            fabTrash.startAnimation(fabClose)
            labelCreate.startAnimation(inRightToLeft)
            labelCategory.startAnimation(inRightToLeft)
            labelTrash.startAnimation(inRightToLeft)
            fabCreate.visibility = View.GONE
            fabCategory.visibility = View.GONE
            fabTrash.visibility = View.GONE
            labelCreate.visibility  = View.GONE
            labelCategory.visibility = View.GONE
            labelTrash.visibility = View.GONE
            isFabOpen = false
        } else {
            fab.startAnimation(rotateForward)
            fabCreate.startAnimation(fabOpen)
            fabCategory.startAnimation(fabOpen)
            fabTrash.startAnimation(fabOpen)
            labelCreate.startAnimation(inLeftToRight)
            labelCategory.startAnimation(inLeftToRight)
            labelTrash.startAnimation(inLeftToRight)
            fabCreate.visibility = View.VISIBLE
            fabCategory.visibility = View.VISIBLE
            fabTrash.visibility = View.VISIBLE
            labelCreate.visibility  = View.VISIBLE
            labelCategory.visibility = View.VISIBLE
            labelTrash.visibility = View.VISIBLE
            isFabOpen = true
        }
    }

}