package com.anip.swamphacks.util

import android.content.Context
import com.squareup.picasso.Picasso

/**
 * Created by anip on 10/01/18.
 */
public class picasso {
    public val Context.picassso: Picasso
        get() = Picasso.with(this)
}