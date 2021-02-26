package ru.geekbrainsproj

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.geekbrainsproj.model.pojo.MovieInfo
import ru.geekbrainsproj.view.DetailMovieActivity
import ru.geekbrainsproj.view_model.MainViewModel

class MovieRecyclerAdapter(private var movieArray: List<MovieInfo>) : RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_recycler, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ratingTxtView.text = movieArray[position].voteAverage.toString()
        holder.nameFilmTxtView.text = movieArray[position].title
        holder.releaseDateTxtView.text = movieArray[position].releaseDate

        Picasso.with(holder.nameFilmTxtView.context)
                .load("https://image.tmdb.org/t/p/w500${movieArray[position].posterPath}")
                .resize(180, 280)
                .centerCrop()
                .into(holder.posterImg)

    }

    fun setData(movieArrayNew: List<MovieInfo>) {
        movieArray = movieArrayNew
        notifyDataSetChanged()
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

        private val mainContainerCardView = itemView.findViewById<CardView>(R.id.mainContainerCardView)

        init {
            mainContainerCardView.setOnClickListener {
                it.context.startActivity(Intent(it.context, DetailMovieActivity::class.java).apply {
                    putExtra(MainViewModel.NAME_FILM, movieArray[adapterPosition].title)
                    putExtra(MainViewModel.DESCRIBE_FILM, movieArray[adapterPosition].overview)
                    putExtra(MainViewModel.POSTER_FILM, movieArray[adapterPosition].posterPath)
                })
            }
        }

    }

}