package com.javlonrahimov1212.footbalteams.models

data class Player(
    val id: Int,
    val image: Int,
    val name: String,
    var club: Clubs
)
