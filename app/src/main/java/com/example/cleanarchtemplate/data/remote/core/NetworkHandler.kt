package com.example.cleanarchtemplate.data.remote.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 * Хорошим тоном будет проверять подключение к сети, прежде чем выполнять сетевые запросы.
 * NetworkHandler обеспечивает эту проверку.
 */
//@Singleton
//class NetworkHandler @Inject constructor(private val context: Context) {
//    val isConnected
//        get() = run {
//            var connect = false
//            context.networkInfo.registerNetworkCallback(NetworkRequest.Builder().build(),
//                object : ConnectivityManager.NetworkCallback() {
//                    override fun onAvailable(network: Network) {
//                        connect = true
//                    }
//
//                    override fun onLost(network: Network) {
//                        connect = false
//                    }
//                })
//            connect
//        }
//}
//
//val Context.networkInfo
//    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

/** BACKLOG [20190910] когда будет время разберись  */
/**
 * @author EvgenySamarin [github](https://github.com/EvgenySamarin)
 * @since 20190923 v1
 */
@Singleton
class NetworkHandler @Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo