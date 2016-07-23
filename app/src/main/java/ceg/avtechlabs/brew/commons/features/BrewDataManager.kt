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
                subscriber.onNext(response.body())
                subscriber.onCompleted()
            }

            subscriber.onError(Throwable(response.message()))
        }
    }
}