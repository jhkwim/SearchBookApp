package com.jhkim.searchbookapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import com.jhkim.core.detail.DetailFragment
import com.jhkim.core.search.REQUEST_KEY_SEARCH
import com.jhkim.core.search.RESULT_kEY_ISBN3
import com.jhkim.core.search.SearchFragment
import com.jhkim.searchbookapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val DETAIL_ARGS = "isbn13"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkSnackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkSnackBar = Snackbar.make(binding.root, R.string.offline, Snackbar.LENGTH_INDEFINITE)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(binding.containerView.id, SearchFragment())
            }
        }

        supportFragmentManager.setFragmentResultListener(REQUEST_KEY_SEARCH, this) { _, result ->
            val isbn13 = result.getString(RESULT_kEY_ISBN3) ?: return@setFragmentResultListener
            showDetailFragment(isbn13)
        }

        viewModel.isNetworkAvailable.observe(this) { isAvailable ->
            if (isAvailable) networkSnackBar.dismiss() else networkSnackBar.show()
        }

        viewModel.updateNetworkState(isNetworkAvailable())
    }

    private fun showDetailFragment(isbn13: String) {
        supportFragmentManager.commit {
            replace(binding.containerView.id, DetailFragment::class.java, bundleOf(DETAIL_ARGS to isbn13))
            addToBackStack(null)
        }
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onStop() {
        super.onStop()

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.d("jhkim", "network available")
            viewModel.updateNetworkState(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d("jhkim", "network lost")
            viewModel.updateNetworkState(false)
        }
    }

}