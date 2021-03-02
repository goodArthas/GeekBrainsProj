package ru.geekbrainsproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class PhoneBookRecyclerAdapter(private var contactArray: List<Pair<String, String>>) : RecyclerView.Adapter<PhoneBookRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.phone_book_item_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (contactArray[position].second == null || contactArray[position].first == null) return
        holder.name.text = contactArray[position].first
        holder.number.text = contactArray[position].second
    }

    fun setData(contactArray: List<Pair<String, String>>) {
        this.contactArray = contactArray
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (contactArray == null || contactArray.isEmpty()) {
            return 0
        } else return contactArray.size

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<AppCompatTextView>(R.id.contactName)
        val number = itemView.findViewById<AppCompatTextView>(R.id.contactNumber)
    }

}