package com.projetb3.movynov.dataclasses

import java.time.LocalDateTime

data class User(val id:Int,val email:String, val hashPassword:String, val dateTime: LocalDateTime, val role:Role)