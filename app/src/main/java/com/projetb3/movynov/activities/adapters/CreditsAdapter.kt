package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.credits.Cast

class CreditsAdapter(private val listOfCast : List<Cast>) : RecyclerView.Adapter<CreditsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false)
        return CreditsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val cast = listOfCast[position]
        holder.castImageView.background = cast.profileImage
        holder.castNameTextView.text = cast.name
        holder.castCharacterTextView.text = cast.character
    }

    override fun getItemCount(): Int {
        return listOfCast.size
    }
}