package id.aasumitro.easynote.ui.main.fragment.trash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import id.aasumitro.easynote.R
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.ui.main.MainActivity
import id.aasumitro.easynote.ui.main.TrashViewModel
import id.aasumitro.easynote.ui.main.fragment.trash.SwipeHelper.UnderlayButtonClickListener
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity

/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class FragmentTrashList : Fragment(), TrashListener {

    private fun mainActivity() = activity as MainActivity
    private val mViewModel : TrashViewModel by lazy {
        ViewModelProviders.of(activity!!).get(TrashViewModel::class.java)
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
            this.title = getString(R.string.text_trash_title)
            this.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.setNavigationOnClickListener {
                mainActivity().onBackPressed()
            }
        }
        mViewModel.initViewModel(this)
        mViewModel.getTrashListTask()
    }

    private fun initRecyclerView(view: View){
        view.itemsRecyclerView.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        view.itemsRecyclerView.layoutManager = layoutManager
        view.itemsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            mViewModel.getTrashListTask()
        }
    }

    override fun initListAdapter() {
        val mAdapter = TrashAdapter(mViewModel.mTrashList)
        itemsRecyclerView.adapter = mAdapter
        val swipeHandler = object : SwipeHelper(activity!!, itemsRecyclerView) {
            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder,
                                                   underlayButtons: MutableList<UnderlayButton>) {
                underlayButtons.add(SwipeHelper.UnderlayButton(
                        getString(R.string.text_delete), ContextCompat.getColor(activity!!, R.color.colorAccent),
                        UnderlayButtonClickListener {
                            activity!!.alert(Appcompat) {
                                title = resources.getString(R.string.msg_sure_question)
                                message = resources.getString(R.string.msg_action_clear)
                                positiveButton(resources.getString(R.string.text_delete)) {
                                    mAdapter.deleteTrashItem(viewHolder.adapterPosition)
                                    mAdapter.notifyDataSetChanged()
                                    snackbar(view!!, getString(R.string.msg_delete_success))
                                }
                                negativeButton(resources.getString(R.string.text_cancel)) { }
                            }.show().setCancelable(false)
                        }
                ))
                underlayButtons.add(SwipeHelper.UnderlayButton(
                        getString(R.string.text_restore), ContextCompat.getColor(activity!!, R.color.colorPrimary),
                        UnderlayButtonClickListener {
                            mAdapter.restoreBack(viewHolder.adapterPosition)
                            activity!!.finish()
                            activity!!.startActivity<MainActivity>()
                            mAdapter.notifyDataSetChanged()
                        }
                ))
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(itemsRecyclerView)
        //itemsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_trash, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val menuAdd = menu.findItem(R.id.menu_add)
        menuAdd.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.menu_clear -> {
                activity!!.alert(Appcompat) {
                    title = resources.getString(R.string.msg_sure_question)
                    message = resources.getString(R.string.msg_action_clear)
                    positiveButton(resources.getString(R.string.text_delete)) {
                        LocalDataSource.deleteAllTrashedNote(true)
                        snackbar(view!!, getString(R.string.msg_delete_success))
                    }
                    negativeButton(resources.getString(R.string.text_cancel)) { }
                }.show().setCancelable(false)
            }
        }
        return true
    }

}