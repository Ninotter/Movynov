package com.projetb3.movynov.dataclasses

data class ForumPost(val id: Int, val title: String, val content: String, val author: User, val date: String, val nbComments: Int)