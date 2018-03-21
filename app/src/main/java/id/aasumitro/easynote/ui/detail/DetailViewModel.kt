package id.aasumitro.easynote.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.aasumitro.easynote.data.GlobalTask
import id.aasumitro.easynote.data.local.LocalDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Agus Adhi Sumitro on 18/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */
class DetailViewModel : ViewModel() {

    private var mTitle = MutableLiveData<String>()
    private var mContent = MutableLiveData<String>()
    private var mLock = MutableLiveData<Boolean>()
    private var mCategory  = MutableLiveData<String>()
    private var mCreatedAt = MutableLiveData<Date>()
    private var mUpdatedAt = MutableLiveData<Date>()

    fun getDetailData(id: Long) {
        LocalDataSource.readNoteById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onSuccess ->
                    mTitle.value = onSuccess.title
                    mContent.value = onSuccess.content
                    mLock.value = onSuccess.isLocked
                    mCategory.value = onSuccess.category
                    mCreatedAt.value = onSuccess.createdAt
                    mUpdatedAt.value = onSuccess.updatedAt
                }, { onError ->

                    onError.printStackTrace()
                    onError.let {

                    }

                })
    }

    fun postNewNotes(title: String,  content: String,
                    isLock: Boolean, category: String) {
        val isTrashed = false
        val currentTime = Date()
        Observable.interval(1, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    LocalDataSource.createNewNote(
                            title, content, isLock, isTrashed,
                            category, currentTime, currentTime)
                }
    }

    fun updateEditedNotes(id:Long, title: String,  content: String,
                       isLock: Boolean, category: String) {
        val updatedAt = Date()
        Observable.interval(1, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                    LocalDataSource.updateNote(
                            id, title, content, isLock,
                            category, updatedAt)
                }
    }

    fun getCategoryList() = GlobalTask.getCategoryList(0)
    fun getTitle(): LiveData<String> = mTitle
    fun getContent(): LiveData<String> = mContent
    fun getLockStatus(): LiveData<Boolean> = mLock
    fun getCategory(): LiveData<String> = mCategory
    fun getDateCreated(): LiveData<Date> = mCreatedAt
    fun getDateUpdated(): LiveData<Date> = mUpdatedAt

}