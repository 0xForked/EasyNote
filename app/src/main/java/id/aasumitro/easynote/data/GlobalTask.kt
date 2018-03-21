package id.aasumitro.easynote.data

import id.aasumitro.easynote.data.local.LocalDataSource
import id.aasumitro.easynote.data.local.model.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 20/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

object GlobalTask {

    var mCategoryList = ArrayList<Category>()

    fun getCategoryList(filter: Long) {
        LocalDataSource.readCategoryFilter(filter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onSuccess ->

                    mCategoryList = onSuccess as ArrayList<Category>

                }, { onError ->
                    onError.printStackTrace()
                    onError.let { }
                })
    }

}