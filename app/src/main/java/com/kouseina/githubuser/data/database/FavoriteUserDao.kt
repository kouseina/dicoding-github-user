package com.kouseina.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteUser: FavoriteUser)

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getAll(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from FavoriteUser WHERE username =:username LIMIT 1")
    fun getById(username: String): LiveData<FavoriteUser>
}