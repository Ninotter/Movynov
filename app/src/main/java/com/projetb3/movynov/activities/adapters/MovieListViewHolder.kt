package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class MovieListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val movieLayout : CardView = itemView.findViewById(R.id.movie_item)
    val movieTitleTextView : TextView = itemView.findViewById(R.id.movie_title)
    val moviePosterImageView : ImageView = itemView.findViewById(R.id.movie_poster)
    val movieOverviewTextView : TextView = itemView.findViewById(R.id.movie_overview)
    val movieReleaseDateTextView : TextView = itemView.findViewById(R.id.movie_release_date)
    val movieRatingTextView : TextView = itemView.findViewById(R.id.movie_vote_average)
    val addToWatchListButton : ImageView = itemView.findViewById(R.id.movie_add_watchlist)

    //A UTILISER POUR SUPPRIMER UN ELEMENT DE LA LISTE
    //fun onClick(v: View) {
    //    //Log.d("View: ", v.toString());
    //    //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
    //    if (v == imgViewRemoveIcon) {
    //        removeAt(getPosition())
    //    } else if (mItemClickListener != null) {
    //       mItemClickListener.onItemClick(v, getPosition())
    //    }
    //}
}
