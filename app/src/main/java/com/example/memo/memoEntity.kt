package com.example.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

//테이블이라는 것을 뜻하는 Entity 어노테이션
@Entity(tableName = "memo")
data class MemoEntity(

    //기본키 자동 증가 설정
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var memo: String = ""
)