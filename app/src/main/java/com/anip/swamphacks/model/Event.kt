package com.anip.swamphacks.model

/**
 * Created by anip on 25/11/17.
 */

data class Event(val id : Int,
                 val name: String,
                 val description: String,
                 val startTime: Long,
                 val endTime: Long,
                 val location: String = "ewrewr",
                 val numAttendees: Int = 12,
                 val type: String = "type",
                 val map: String = "map",
                 val avgRating : Float = 1.0f)
{
    constructor() : this(12,name = "Anip",description = "",startTime = 1000, endTime = 1000, location = "", numAttendees = 0, type = "",map = "", avgRating = 0.5F) // this constructor is an explicit
    // "empty" constructor, as seen by Java.
}
