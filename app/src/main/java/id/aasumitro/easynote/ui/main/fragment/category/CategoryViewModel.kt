package id.aasumitro.easynote.ui.main.fragment.category

import android.arch.lifecycle.ViewModel
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Agus Adhi Sumitro on 19/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

class CategoryViewModel : ViewModel() {

    private var mListener: CategoryListener? = null
    var mCategoryList = ArrayList<Category>()

    fun initViewHolder(listener: CategoryListener ) {
        mListener = listener
    }

    fun getCategoryList() {
        LocalDataSource.readCategoryFilter(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onSuccess ->

                    mCategoryList= onSuccess as ArrayList<Category>
                    mListener!!.initListAdapter()

                }, { onError ->
                    onError.printStackTrace()
                    onError.let { }
                })
    }

}