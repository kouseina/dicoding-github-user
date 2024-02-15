package com.kouseina.githubuser.ui.detailUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kouseina.githubuser.data.database.FavoriteUser
import com.kouseina.githubuser.data.repository.FavoriteUserRepository
import com.kouseina.githubuser.data.response.DetailUserResponse
import com.kouseina.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isUserFavorite = MutableLiveData<Boolean>()
    val isUserFavorite: LiveData<Boolean> = _isUserFavorite

    val username = MutableLiveData<String>()

    fun fetchDetailUser() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username.value ?: "")
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>, response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(DetailUserFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailUserFragment.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun fetchFavoriteUser() {
        favoriteUserRepository.getById(username.value ?: "").observeForever {
            _isUserFavorite.value = it != null
//            println("favorite user get by id : ${it}")
        }
    }

    fun addFavorite(favoriteUser: FavoriteUser) {
        favoriteUserRepository.insert(favoriteUser)
        fetchFavoriteUser()
    }

    fun removeFavorite(favoriteUser: FavoriteUser) {
        favoriteUserRepository.delete(favoriteUser)
        fetchFavoriteUser()
    }
}