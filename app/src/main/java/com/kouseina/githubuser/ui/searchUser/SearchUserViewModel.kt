package com.kouseina.githubuser.ui.searchUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kouseina.githubuser.data.response.ItemsItem
import com.kouseina.githubuser.data.response.SearchUserResponse
import com.kouseina.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel : ViewModel() {
    companion object {
//        const val TAG = "SearchUserFragment"
    }

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchSearchUser()
    }

    fun fetchSearchUser(q: String = "daffa") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(q = q)
        client.enqueue(object: Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.items != null) {
                        _userList.value = responseBody?.items!!
                    }
                } else {
                    Log.e(SearchUserFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(SearchUserFragment.TAG, "onFailure: ${t.message}")
            }

        })
    }
}