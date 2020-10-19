package com.example.memo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//데이터 베이스 설정
//어떤 테이블을 사용할지 arrayof안에 명시
@Database(entities = arrayOf(MemoEntity::class), version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDAO(): MemoDAO

    companion object {
        //싱글톤 패턴
        var INSTANCE: MemoDatabase? = null

        fun getInstance(context: Context) : MemoDatabase? {
            if (INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration()  //db를 한번생성하고 나서 동일한 스키마로 가는것이 아니라 중간에 수정이 있을때 버전을 확인하고 새로운 버전이면 이전꺼는 삭제한다
                        .build()
                }
            }

            return INSTANCE
        }
    }
}