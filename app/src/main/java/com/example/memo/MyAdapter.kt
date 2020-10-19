package com.example.memo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*

class MyAdapter(
    val context: Context,
    var list: List<MemoEntity>,
    var onDeleteListener: OnDeleteListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val memo = itemView.textview_memo
        val root = itemView.root
    }

    //아이템(틀)을 생성하는 함수
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)

        return MyViewHolder(itemView) //이 리턴값이 onBindViewHolder()로 넘어감
    }

    //아이템이 몇개인지 카운트하는 함수
    override fun getItemCount(): Int {
        return list.size
    }

    //틀과 내용을 합쳐주는 함수
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //list = 1,2,3,,,,,
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                onDeleteListener.onDeleteListener(memo)
                return true
            }

        })
    }
}