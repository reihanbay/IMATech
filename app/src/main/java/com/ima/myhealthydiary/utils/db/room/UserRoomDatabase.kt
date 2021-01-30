package com.ima.myhealthydiary.utils.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    companion object{
        private var INSTANCE: UserRoomDatabase? = null

        fun getUserDatabase(context: Context): UserRoomDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                UserRoomDatabase::class.java, "user_room.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}