package com.anip.swamphacks.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.anip.swamphacks.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by anip on 11/11/17.
 */
class ProfileFragment : Fragment() {
//    public final static int WIDTH=500;
    var WIDTH : Int = 500;
    companion object {
        fun newInstance(): ProfileFragment {
            var fragmentHome = ProfileFragment()
            var args = Bundle()
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_profile, container, false)
        var image = rootView.findViewById<ImageView>(R.id.QRCode)
        try {
            // some code
            var bitmap : Bitmap = encodeAsBitmap("Anip Mehta")
            println(bitmap)
            var image = rootView.findViewById<ImageView>(R.id.QRCode)
            image.setImageBitmap(bitmap)
        }
        catch (e: WriterException) {
            // handler
            e.printStackTrace()
        }
        return rootView
    }

    private fun encodeAsBitmap(s: String): Bitmap {
        var result : BitMatrix = BitMatrix(100, 100)
        try {
            // some code
            result = MultiFormatWriter().encode(s,  BarcodeFormat.QR_CODE,
                    WIDTH, WIDTH, null)
        }
        catch (e: IllegalArgumentException) {
            // handler
            e.printStackTrace()
        }

        var w : Int  = result.width
        var h : Int = result.height
        var pixels = IntArray(w*h) { 0 }
        println(pixels[0])
        println("Width"+w)
        println("Height"+h)
        for (i in 0..(h-1)) {
            var offset = i*w
            for(j in 0..(w-1)){
                if(result.get(j,i)){
                    pixels[offset + j] = Color.BLACK
                }
                else
                    pixels[offset + j] = Color.WHITE
            }

        }
        var bitmap  : Bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels,0, 500, 0,0,w,h)

    return bitmap
    }


}