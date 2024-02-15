package com.kouseina.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kouseina.githubuser.data.database.FavoriteUser
import com.kouseina.githubuser.data.database.FavoriteUserDao
import com.kouseina.githubuser.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabse(application)
        favoriteUserDao = db.favoriteUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { favoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { favoriteUserDao.delete(favoriteUser) }
    }

    fun getAll(): LiveData<List<FavoriteUser>> = favoriteUserDao.getAll()

//    fun isUserFavorite(username: String): Boolean =
//        favoriteUserDao.getById(username).value?.isNotEmpty() ?: false

    fun getById(username: String): LiveData<FavoriteUser> =
        favoriteUserDao.getById(username)
}