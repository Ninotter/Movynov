package com.projetb3.movynov.activities.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R

class CreditsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val castCard : CardView = itemView.findViewById(R.id.cast_item_cardview)
    val castConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.cast_item_constraintlayout)
    val castImageView : ImageView = itemView.findViewById(R.id.cast_item_profile_image)
    val castNameTextView : TextView = itemView.findViewById(R.id.cast_item_name)
    val castCharacterTextView : TextView = itemView.findViewById(R.id.cast_item_character_name)

}