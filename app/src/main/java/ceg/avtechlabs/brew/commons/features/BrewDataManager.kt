package ceg.avtechlabs.brew.commons.features

import android.util.Log
import ceg.avtechlabs.brew.api.RestApi
import ceg.avtechlabs.brew.model.Brew
import rx.Observable

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

class BrewDataManager(private val api: RestApi = RestApi()) {
    fun pour(): Observable<Brew> {
        return Observable.create {
            subscriber ->
            val callResponse = api.brew()
            val response = callResponse.execute()

            if(response.isSuccessful) {
                Log.d("brewdata", response.body().data.url)
                subscriber.onNext(response.body())
                subscriber.onCompleted()
            }else{
                Log.d("brewerror", response.message())
            }

            subscriber.onError(Throwable(response.message()))
        }
    }
}