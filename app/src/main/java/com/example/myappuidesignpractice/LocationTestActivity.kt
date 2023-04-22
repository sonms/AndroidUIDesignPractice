package com.example.myappuidesignpractice

import android.content.pm.PackageManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myappuidesignpractice.databinding.ActivityLocationTestBinding
import com.google.android.gms.location.*

class LocationTestActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityLocationTestBinding
    private val REQUEST_PERMISSION_LOCATION = 10
    //현재 위치를 가져오기 위한 변수
    private var mCurrentLocation : FusedLocationProviderClient? = null
    //위치 값을 가지고 있는 객체
    lateinit var mLastLocation : Location
    //위치 정보 요청의 매개변수를 저장하는 변수
    private lateinit var mLocationRequest : LocationRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location_test)
        mBinding = ActivityLocationTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //이 메서드는 요청의 우선순위를 설정하여 Google Play 서비스 위치 서비스에 사용할 위치 소스에 관한 강력한 힌트를 제공합니다. 다음과 같은 값이 지원됩니다.
        //PRIORITY_HIGH_ACCURACY = 이 설정을 사용하여 가장 정확한 위치를 요청합니다. 이 설정을 사용하면 위치 서비스가 GPS를 사용하여 위치를 확인할 가능성이 높습니다.
        //
        mLocationRequest =  LocationRequest.create().apply {
            interval = 10000 //앱에서 선호하는 위치 업데이트 수신 간격
            fastestInterval = 5000 //앱이 위치 업데이트를 가장 빠르게 처리할 수 있는 간격
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }
        val permissionCheckData = intent.getBooleanExtra("grant", false)

        mBinding.button.setOnClickListener {
            if (permissionCheckData || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                currentLocationUpdates()
            } else {
                Toast.makeText(this, "위치 권한이 없습니다. 설정에 가서 권한 체크를 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        mBinding.textview.text = "위도 : ${mLastLocation.latitude} , 경도 : ${mLastLocation.longitude}"
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    private fun currentLocationUpdates() {
        mCurrentLocation = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        mCurrentLocation!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
    }
}