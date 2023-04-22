package com.example.myappuidesignpractice

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.myappuidesignpractice.databinding.ActivityLocationTestBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationTestActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding : ActivityLocationTestBinding
    private val REQUEST_PERMISSION_LOCATION = 10
    //현재 위치를 가져오기 위한 변수
    private var mCurrentLocation : FusedLocationProviderClient? = null
    //위치 값을 가지고 있는 객체
    lateinit var mLastLocation : Location
    //data class mLocation(val a : Double, val b : Double)
    private var lArr = mutableListOf<LocationData>()
    //위치 정보 요청의 매개변수를 저장하는 변수
    private lateinit var mLocationRequest : LocationRequest
    //구글 맵
    private lateinit var mMap : GoogleMap
    //private lateinit var mapFragment : SupportMapFragment
    //위치 값 요청에 대한 갱신 정보를 받는 변수
    lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location_test)
        mBinding = ActivityLocationTestBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
        //이 메서드는 요청의 우선순위를 설정하여 Google Play 서비스 위치 서비스에 사용할 위치 소스에 관한 강력한 힌트를 제공합니다. 다음과 같은 값이 지원됩니다.
        //PRIORITY_HIGH_ACCURACY = 이 설정을 사용하여 가장 정확한 위치를 요청합니다. 이 설정을 사용하면 위치 서비스가 GPS를 사용하여 위치를 확인할 가능성이 높습니다.
        //둘
        mLocationRequest =  LocationRequest.create().apply {
            interval = 10000 //앱에서 선호하는 위치 업데이트 수신 간격
            fastestInterval = 5000 //앱이 위치 업데이트를 가장 빠르게 처리할 수 있는 간격
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        val permissionCheckData = intent.getBooleanExtra("grant", false)
        mBinding.button.setOnClickListener {
            if (permissionCheckData || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //currentLocationUpdates() //둘
                initGoogleMap() //첫
            } else {
                Toast.makeText(this, "위치 권한이 없습니다. 설정에 가서 권한 체크를 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }
    //첫
    fun initGoogleMap(){
        //구글 맵을 준비하는 작업을 진행한다
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync (this)

    }
    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    //둘
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        mBinding.textview.text = "위도 : ${mLastLocation.latitude} , 경도 : ${mLastLocation.longitude}"
        lArr.add(LocationData(mLastLocation.latitude, mLastLocation.longitude))
    }
    //둘
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    //둘
    private fun currentLocationUpdates() {
        mCurrentLocation = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        mCurrentLocation!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }

    //첫
    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val locationRequest = LocationRequest.create()
        locationRequest.run{
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        locationCallback = object :LocationCallback(){
            //1초에 한번씩 변경된 위치 정보가 onLocationResult 으로 전달된다.
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult?.let{
                    Log.d("위치정보",  "위도: ${it.locations[0].latitude} 경도: ${it.locations[0].longitude}")
                    //   setLastLocation(location) //계속 실시간으로 위치를 받아오고 있기 때문에 맵을 확대해도 다시 줄어든다.
                    setLocation(it.locations[0].latitude, it.locations[0].longitude)

                   /* for (location in it.locations){
                        Log.d("위치정보",  "위도: ${location.latitude} 경도: ${location.longitude}")
                        //   setLastLocation(location) //계속 실시간으로 위치를 받아오고 있기 때문에 맵을 확대해도 다시 줄어든다.
                        setLocation(location.latitude, location.longitude)
                    }*/
                }
            }
        }

        //권한 처리
        mCurrentLocation!!.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper())

    }
    //첫
    fun setLocation(latitude:Double,longitude:Double) {
        val currentLocation = LatLng(latitude, longitude)

        val makerOptions = MarkerOptions().position(currentLocation).title("I am here")
        val cameraPosition = CameraPosition.Builder().target(currentLocation).zoom(15.0f).build()

        mMap.clear()
        mMap.addMarker(makerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    //첫
    override fun onMapReady(googlemap: GoogleMap) {
        mMap = googlemap
        mCurrentLocation = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
        //updateLocation()
        /*val markerOptions = MarkerOptions()

        val currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        //val currentLocation = LatLng(37.556, 126.97)
        markerOptions.position(currentLocation)
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20F))
    */}


}