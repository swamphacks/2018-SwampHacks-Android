package com.anip.swamphacks.model

/**
 * Created by anip on 25/11/17.
 */

data class Event(val name: String,
                 val description: String,
                 val startTime: Long,
                 val endTime: Long,
                 val location: String,
                 val numAttendees: Int,
                 val type: String,
                 val map: String,
                 val avgRating : Float)
{
    constructor() : this(name = "Anip",description = "",startTime = 1000, endTime = 1000, location = "", numAttendees = 0, type = "",map = "", avgRating = 0.5F) // this constructor is an explicit
    // "empty" constructor, as seen by Java.
}
