package ru.geekbrainsproj

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrainsproj.model.MovieData

class MovieRecyclerAdapter(private val movieArray: ArrayList<MovieData> = ArrayList()) : RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_recycler, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.posterImg.setImageResource(R.drawable.poster)
        holder.ratingTxtView.text = movieArray[position].rating
        holder.nameFilmTxtView.text = movieArray[position].name
        holder.releaseDateTxtView.text = movieArray[position].releaseDate
    }

    override fun getItemCount(): Int {
        if (movieArray == null || movieArray.size == 0) {
            return 0
        } else return movieArray.size

    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val posterImg = itemView.findViewById<AppCompatImageView>(R.id.imgView_poster)
        val ratingTxtView = itemView.findViewById<AppCompatTextView>(R.id.txtView_rating)
        val nameFilmTxtView = itemView.findViewById<AppCompatTextView>(R.id.txtView_nameFilm)
        val releaseDateTxtView = itemView.findViewById<AppCompatTextView>(R.id.txtView_releaseDate)

    }

}