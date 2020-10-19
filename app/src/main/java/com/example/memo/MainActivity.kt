package com.example.memo

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnDeleteListener {

    //나중에 초기화
    lateinit var db: MemoDatabase

    var  memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = MemoDatabase.getInstance(this)!!

        button_add.setOnClickListener {
            val memo = MemoEntity(null, edittext_memo.text.toString())
            edittext_memo.setText("")
            insertMemo(memo)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        getAllMemos()
    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data

    //4. Set RecyclerView

    fun insertMemo(memo: MemoEntity){
        //1. mainThread vs workerThread(Background Thread)
        //모든 ui관련 일은 메인쓰레드에서 진행한다.
        //모든 데이터 통신 관련 일은 워커쓰레드에서 진행한다.
        //쓰레드 관리를 쉽게 하려면 코루틴을 배우면 된다.
        //하지만 여기선 기본적인 Asynctask를 사용하겠다

        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                //실행햇을때 백그라운드에서 무슨일을 할지 정의
                db.memoDAO().insert(memo)
            }


            //실행 이후에 확인하는 함수
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()

            }

        }

        insertTask.execute()
    }

    fun getAllMemos(){
        val getTask = (object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                memoList = db.memoDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }

        }).execute()
    }

    fun deleteMemo(memo: MemoEntity){
        val deleteTask = object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.memoDAO().delete(memo)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAllMemos()
            }
        }

        deleteTask.execute()
    }

    fun setRecyclerView(memoList : List<MemoEntity>){
        recyclerView.adapter = MyAdapter(this, memoList, this)
    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }
}