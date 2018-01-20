package com.anip.swamphacks.model

/**
 * Created by anip on 07/01/18.
 */
data class Announcement(val id: Int,
                        val name: String,
                        val description: String,
                        val type : String)

{
    constructor() : this(id = 124, name = "SwampHacks",description = "Welcome to SwampHacks", type = "Food") // this constructor is an explicit
    // "empty" constructor, as seen by Java.
}
