package com.example.kotlin1

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.MaterialSearchBar.OnSearchActionListener
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, Filterable {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    //private lateinit var searchbar:MaterialSearchBar
    private lateinit var searchbar:SearchView
    private lateinit var  predictionList:List<AutocompletePrediction>
    private lateinit var   placesClient: PlacesClient
    private lateinit var token:AutocompleteSessionToken
    // 1
    private lateinit var locationCallback: LocationCallback
    // 2
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //searchbar=findViewById(R.id.searchbar)
        if(!Places.isInitialized()){
        Places.initialize(applicationContext, "AIzaSyC2wNspnqTXT1Rs5KSW-QXWliu6eLR-PaQ")}
        placesClient=Places.createClient(this)
        token = AutocompleteSessionToken.newInstance()
        val autocompletefrag=supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompletefrag.setTypeFilter(TypeFilter.ADDRESS)
        autocompletefrag.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
          autocompletefrag.setOnPlaceSelectedListener (object:PlaceSelectionListener {
              override fun onPlaceSelected(p0: Place) {
                  Log.i("placeselect", "Place: " + p0.getName() + ", " + p0.getId());
              }

              override fun onError(p0: Status) {
                  Log.i("error", "An error occurred: " + p0);              }


          }


          )
        createLocationRequest()



//        searchbar.setOnSearchActionListener(object : OnSearchActionListener {
//            override fun onSearchStateChanged(enabled: Boolean) {}
//            override fun onSearchConfirmed(text: CharSequence) {
//                startSearch(text.toString(), true, null, true)
//            }
//
//            override fun onButtonClicked(buttonCode: Int) {
//                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) { //opening or closing a navigation drawer
//                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
//                    searchbar.disableSearch()
//                }
//            }
//        })
//        searchbar.addTextChangeListener(object: TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val predictionsRequest =
//                    FindAutocompletePredictionsRequest.builder()
//                        .setTypeFilter(TypeFilter.ADDRESS)
//                        .setSessionToken(token)
//                        .setQuery(s.toString())
//                        .build()
//
//                placesClient.findAutocompletePredictions(predictionsRequest)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val predictionsResponse: FindAutocompletePredictionsResponse? = task.getResult()
//                            if (predictionsResponse != null) {
//                                predictionList = predictionsResponse.autocompletePredictions
//                                val suggestionsList: MutableList<String> = ArrayList()
//                                for (i in 0 until predictionList.size) {
//                                    val prediction: AutocompletePrediction = predictionList.get(i)
//                                    suggestionsList.add(prediction.getFullText(null).toString())
//                                }
//                                searchbar.updateLastSuggestions(suggestionsList)
//                                if (!searchbar.isSuggestionsVisible()) {
//                                    searchbar.showSuggestionsList()
//                                }
//                            }
//                        } else {
//                            Log.i("mytag", "prediction fetching task unsuccessful")
//                        }
//                    }
//                    .addOnFailureListener{exception->
//                        if(exception is IOException){
//                            Log.i("exception","api exception")
//                        }
//
//                    }
//
//            }
//
//        })


          }







    private fun getAddress(latLng: LatLng): String {
        // 1
        val geocoder = Geocoder(this)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)

                }
            }

            println(addressText)
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    override fun onMarkerClick(p0: Marker?) = false


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        // 1
        mMap.isMyLocationEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN


// 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }
    private fun getdevicelocation(){

    }
    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines
        markerOptions.title(titleStr)

        mMap.addMarker(markerOptions)
    }

    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        //2
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 10000
        // 3
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(this@MapsActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }



    // 2
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // 3
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

        override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                token = AutocompleteSessionToken.newInstance()

                var suggestionsList: MutableList<String> = ArrayList()
                if(constraint.toString().isEmpty()){



                }
                else {
                    val predictionsRequest =
                        FindAutocompletePredictionsRequest.builder()
                            .setTypeFilter(TypeFilter.ADDRESS)
                            .setSessionToken(token)
                            .setQuery(constraint.toString())
                            .build()

                    placesClient.findAutocompletePredictions(predictionsRequest)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val predictionsResponse: FindAutocompletePredictionsResponse? = task.getResult()
                                if (predictionsResponse != null) {
                                    predictionList = predictionsResponse.autocompletePredictions

                                    for (i in 0 until predictionList.size) {
                                        val prediction: AutocompletePrediction = predictionList.get(i)
                                        suggestionsList.add(prediction.getFullText(null).toString())
                                    }

                                }
                            } else {
                                Log.i("mytag", "prediction fetching task unsuccessful")
                            }
                        }
                        .addOnFailureListener { exception ->
                            if (exception is IOException) {
                                Log.i("exception", "api exception")
                            }

                        }

                }





                val filterresults=FilterResults()
                filterresults.values=suggestionsList
                return filterresults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                //publish to list
                val events = filterResults
                println(events.values)

            }

        }
    }



}
