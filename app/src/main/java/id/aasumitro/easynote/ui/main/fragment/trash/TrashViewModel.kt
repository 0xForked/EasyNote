package id.aasumitro.easynote.ui.main

import android.arch.lifecycle.ViewModel
import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Notes
import id.aasumitro.easynote.ui.main.fragment.trash.TrashListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Agus Adhi Sumitro on 16/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class TrashViewModel : ViewModel() {

    private var mListener: TrashListener? = null
    var mTrashList = ArrayList<Notes>()

    fun initViewModel(listener: TrashListener) {
        mListener = listener
    }

    fun getTrashListTask() {
        LocalDataSource.readAllNotes(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext { onSuccess ->
                    mTrashList = onSuccess as ArrayList<Notes>
                    mListener!!.initListAdapter()
                }
                .doOnError { onError ->
                    onError.printStackTrace()
                    onError.let { }
                }
                .subscribe()
    }


}