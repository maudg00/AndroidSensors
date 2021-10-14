package com.example.sensoresinit

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.androidmarcha.Model

class MainActivity : AppCompatActivity(), Model.Companion.SensorManagerInterface {
    private var TAG="SensorList"
    private lateinit var myModel:Model
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myModel=Model()
        myModel.sm=applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = myModel.sm.getSensorList(Sensor.TYPE_ALL)
        Log.d(TAG, deviceSensors.toString())
        myModel.startTakingData(this)
    }
    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        myModel.stopUpdates()

    }
    override fun onResume() {
        super.onResume()
        myModel.sm=applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager
        myModel.startTakingData(this)
    }
    @SuppressLint("SetTextI18n")
    override fun listenToSensorValues(
        accelerometerX: Float, accelerometerY: Float, accelerometerZ: Float,
        gyroscopeX: Float, gyroscopeY: Float, gyroscopeZ: Float,
        magneticX:Float, magneticY:Float, magneticZ: Float,
        temperature: Float, pressure: Float, humidity: Float, light: Float
    ) {
        val accelerometerResult=findViewById<TextView>(R.id.valueAccelerometer)
        val gyroscopeResult=findViewById<TextView>(R.id.valueGyroscope)
        val magneticResult=findViewById<TextView>(R.id.valueMagnetometer)
        val temperatureResult=findViewById<TextView>(R.id.valueTemperature)
        val pressureResult=findViewById<TextView>(R.id.valuePressure)
        val humidityResult=findViewById<TextView>(R.id.valueHumidity)
        val lightResult=findViewById<TextView>(R.id.valueLight)
        accelerometerResult.text = "x:${accelerometerX} y:${accelerometerY} z:${accelerometerZ}"
        gyroscopeResult.text = "x:${gyroscopeX} y:${gyroscopeY} z:${gyroscopeZ}"
        magneticResult.text="x:${magneticX} y:${magneticY} z:${magneticZ}"
        temperatureResult.text = "$temperature"
        pressureResult.text = "$pressure"
        humidityResult.text = "$humidity"
        lightResult.text = "$light"
    }
}