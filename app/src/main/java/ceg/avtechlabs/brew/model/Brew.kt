package ceg.avtechlabs.brew.model

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

data class Brew(val statusCode: Int, val versionCode: Int, val data: Data)

data class Data(val url: String, val title: String, val info: String)