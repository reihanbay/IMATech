package com.ima.myhealthydiary.utils.db.room

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM user where user_email LIKE :email AND password LIKE :password")
    fun getUser(email: String?, password: String?): UserEntity?

    @Update
    fun update(status: UserEntity)
}