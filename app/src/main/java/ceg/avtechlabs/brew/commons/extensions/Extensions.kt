package ceg.avtechlabs.brew.commons.extensions

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import ceg.avtechlabs.brew.R
import com.squareup.picasso.Picasso

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

fun ImageView.loadImage(url: String){
    if(TextUtils.isEmpty(url))
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    else
        Picasso.with(context).load(url).placeholder(R.drawable.progress_animation).into(this)
}

fun TextView.setQuiveraFont(){
    val typeface = Typeface.createFromAsset(context.getAssets(), "quivira.otf")
    this.typeface = typeface
}