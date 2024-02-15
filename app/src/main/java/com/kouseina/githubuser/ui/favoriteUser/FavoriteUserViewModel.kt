package com.kouseina.githubuser.ui.favoriteUser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kouseina.githubuser.data.database.FavoriteUser
import com.kouseina.githubuser.data.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAll(): LiveData<List<FavoriteUser>> = favoriteUserRepository.getAll()
}