package id.aasumitro.easynote.data.local

import id.aasumitro.easynote.data.local.model.Category
import id.aasumitro.easynote.data.local.model.Join
import id.aasumitro.easynote.data.local.model.Notes
import id.aasumitro.easynote.data.local.room.CreatorDB
import io.reactivex.Flowable
import java.util.*


/**
 * Created by Agus Adhi Sumitro on 15/02/2018.
 * https://asmith.my.id
 * aasumitro@gmail.com
 */

object LocalDataSource {

    private val mNoteDao = CreatorDB.mDatabase.notesDao()
    private val mCategoryDao = CreatorDB.mDatabase.categoryDao()
    private val mJoinDAO = CreatorDB.mDatabase.joinDAO()

    fun createNewNote(title: String, content: String, isLock: Boolean,
                      isTrashed: Boolean, category: String, createdAt: Date,
                      updatedAt: Date) {
        val notes = Notes(
                null, title, content, isLock, isTrashed,
                category, createdAt, updatedAt)
        mNoteDao.postNotes(notes)
    }

    fun readAllNotes(isTrashed: Boolean): Flowable<List<Notes>> =
            mNoteDao.getAllNotes(isTrashed)

    fun readNoteByCategory(category: String): Flowable<List<Notes>> =
            mNoteDao.getNotesByCategory(category)

    fun readNoteByTitle(title: String): Flowable<List<Notes>> =
            mNoteDao.getNoteByTitle(title)

    fun readNoteById(id: Long): Flowable<Notes> = LocalDataSource.mNoteDao.getNoteById(id)

    fun updateNote(id: Long, title: String, content: String,
                   isLocked: Boolean, category: String, updatedAt: Date) =
            mNoteDao.putNotes(id, title, content, isLocked, category, updatedAt)

    fun updateNoteCategoryIfDeleted(default: String, categoryDeleted: String) =
            mNoteDao.updateCategory(default, categoryDeleted)

    fun updateIsTrashed(id: Long, isTrash: Boolean) = LocalDataSource.mNoteDao.deleteNoteById(id, isTrash)

    fun deleteNotePermanentById(id: Long) = LocalDataSource.mNoteDao.deleteNotePermanentlyById(id)

    fun deleteAllTrashedNote(isTrash: Boolean) = mNoteDao.clearTrash(isTrash)

    fun createNewCategory(name: String, desc: String, color: String) {
        val created = Date()
        val category = Category(
                null, name, desc,
                color, created)
        mCategoryDao.postCategory(category)
    }

    fun readCategoryFilter(id: Long): Flowable<List<Category>> = mCategoryDao.getCategoryList(id)

    fun deleteCategoryById(id: Long) = mCategoryDao.deleteCategoryById(id)

    fun readJoinedData(isTrashed: Boolean): Flowable<List<Join>> = mJoinDAO.getJoinData(isTrashed)

}