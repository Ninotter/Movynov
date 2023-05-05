package com.projetb3.movynov.dataclasses

import java.util.Date

data class MediaMovie(val id:Int, val title:String, val synopsis: String, val catchLine:String, val rating:Double, val releaseDate:Date, val isInWatchList:Boolean)