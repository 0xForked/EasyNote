package id.aasumitro.easynote.ui.main.fragment.category

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.*
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Category
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.util.DialogUtil
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import org.jetbrains.anko.alert



/**
 * Created by Agus Adhi Sumitro on 19/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class FragmentCategoryList : Fragment(), CategoryListener, RecyclerListener {

    private fun mainActivity() = activity as MainActivity

    private val mViewModel: CategoryViewModel by lazy {
        ViewModelProviders.of(activity!!).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)
        itemsRecyclerView.let { initRecyclerView(view) }
        view.fab.visibility = View.GONE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity().getToolbar().apply {
            this.title = getString(R.string.text_category_list)
            this.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.setNavigationOnClickListener {
                mainActivity().onBackPressed()
            }
        }
        mViewModel.initViewHolder(this)
        mViewModel.getCategoryList()
    }

    private fun initRecyclerView(view: View){
        val numberOfColumns = 3
        view.itemsRecyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(activity, numberOfColumns)
        view.itemsRecyclerView.layoutManager = layoutManager
        view.itemsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false

        }
    }

    override fun initListAdapter() {
        val mAdapter = CategoryAdapter(mViewModel.mCategoryList, this)
        itemsRecyclerView.adapter = mAdapter
    }

    override fun onItemClick(category: Category) {
        activity!!.alert {
            title = getString(R.string.msg_delete_category)
            positiveButton(getString(R.string.text_delete)) {
                LocalDataSource.deleteCategoryById(category.id!!)
                LocalDataSource.updateNoteCategoryIfDeleted("Category", category.name!!)
            }
            negativeButton(getString(R.string.text_cancel)) { }
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_trash, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val deleteIcon = menu.findItem(R.id.menu_clear)
        deleteIcon.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.menu_add -> DialogUtil().createCategoryDialog(activity!!)
        }
        return true
    }

}