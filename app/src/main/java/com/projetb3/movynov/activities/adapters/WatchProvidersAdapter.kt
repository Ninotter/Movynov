package com.projetb3.movynov.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projetb3.movynov.R
import com.projetb3.movynov.dataclasses.watchproviders.Flatrate

class WatchProvidersAdapter(private val listOfProviders : List<Flatrate>) : RecyclerView.Adapter<WatchProvidersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchProvidersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_watchprovider, parent, false)
        return WatchProvidersViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchProvidersViewHolder, position: Int) {
        holder.watchProviderLogo.background = listOfProviders[position].logoImage
    }

    override fun getItemCount(): Int {
        return listOfProviders.size
    }


}