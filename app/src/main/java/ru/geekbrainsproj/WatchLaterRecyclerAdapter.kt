package ru.geekbrainsproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrainsproj.model.room.WatchLaterFilmEntity

class WatchLaterRecyclerAdapter(private var movieArray: List<WatchLaterFilmEntity>) : RecyclerView.Adapter<WatchLaterRecyclerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.watch_later_item_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameFilmTxtView.text = movieArray[position].nameFilm
        holder.timeStampTxtView.text = movieArray[position].data
    }

    fun setData(movieArrayNew: List<WatchLaterFilmEntity>) {
        movieArray = movieArrayNew
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (movieArray == null || movieArray.size == 0) {
            return 0
        } else return movieArray.size

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameFilmTxtView = itemView.findViewById<AppCompatTextView>(R.id.filmName)
        val timeStampTxtView = itemView.findViewById<AppCompatTextView>(R.id.timeStamp)
    }

}