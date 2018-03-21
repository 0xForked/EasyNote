package id.aasumitro.easynote.ui.main.fragment.main

import android.arch.lifecycle.ViewModel
import id.aasumitro.easynote.data.GlobalTask
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Join
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class MainViewModel : ViewModel() {

    private var mListener: MainListener? = null
    var mJoinedList = ArrayList<Join>()

    fun initViewModel(listener: MainListener) {
        this.mListener = listener
    }

    fun getListTask() {
        LocalDataSource.readJoinedData(false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onSuccess ->

                    mJoinedList = onSuccess as ArrayList<Join>
                    mListener!!.initListAdapter()

                }, { onError ->

                    onError.printStackTrace()
                    onError.let { }

                })
    }

    fun getCategoryList() = GlobalTask.getCategoryList(0)

}