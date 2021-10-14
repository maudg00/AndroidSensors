package com.example.androidmarcha

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

import android.util.Log
import java.lang.Exception


class Model: SensorEventListener{
    private val TAG="Model"
    lateinit var sm: SensorManager
    //private var listener:SensorEventListener=this
    private var accelerometerX:Float=0f
    private var accelerometerY:Float=0f
    private var accelerometerZ:Float=0f
    private var gyroscopeX:Float=0f
    private var gyroscopeY:Float=0f
    private var gyroscopeZ:Float=0f
    private var magneticX:Float=0f
    private var magneticY:Float=0f
    private var magneticZ:Float=0f
    private var temperature:Float=0f
    private var light:Float=0f
    private var humidity:Float=0f
    private var pressure:Float=0f
    private lateinit var mySensorListener:SensorManagerInterface
    fun startTakingData(context: Context)
    {
        Log.d(TAG,"START")
        try {
            mySensorListener=context as SensorManagerInterface
        }
        catch (e:Exception){
            Log.d(TAG,"Error: Context does not implement SensorManagerInterface.")
            return
        }
        sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{
                accelerometer->
            Log.d(TAG,"ACCELEROMETER")
            sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        }
        sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also{
                gyroscope->
            Log.d(TAG,"GYROSCOPE")
            sm.registerListener(this, gyroscope,SensorManager.SENSOR_DELAY_UI)
        }
        sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also{
                magnetic->
            Log.d(TAG,"MAGNETIC")
            sm.registerListener(this, magnetic,(1/60)*1000)
        }
        sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.also{
                temperature->
            Log.d(TAG,"TEMPERATURE")
            sm.registerListener(this, temperature,SensorManager.SENSOR_DELAY_FASTEST)
        }
        sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)?.also{
                humidity->
            Log.d(TAG,"HUMIDITY")
            sm.registerListener(this, humidity,SensorManager.SENSOR_DELAY_GAME)
        }
        sm.getDefaultSensor(Sensor.TYPE_PRESSURE)?.also{
                pressure->
            Log.d(TAG,"PRESSURE")
            sm.registerListener(this, pressure,SensorManager.SENSOR_DELAY_NORMAL)
        }
            sm.getDefaultSensor(Sensor.TYPE_LIGHT)?.also{
                light->
            Log.d(TAG,"LIGHT")
            sm.registerListener(this, light,SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(event!=null)
        {
            Log.d(TAG,"CHANGE in ${event.sensor.stringType}")
            when(event.sensor.type)
            {
                Sensor.TYPE_ACCELEROMETER->{
                    accelerometerX=event.values[0]
                    accelerometerY=event.values[1]
                    accelerometerZ=event.values[2]
                }
                Sensor.TYPE_GYROSCOPE->{
                    gyroscopeX=event.values[0]
                    gyroscopeY=event.values[1]
                    gyroscopeZ=event.values[2]
                }
                Sensor.TYPE_MAGNETIC_FIELD->{
                    magneticX=event.values[0]
                    magneticY=event.values[1]
                    magneticZ=event.values[2]
                }
                Sensor.TYPE_AMBIENT_TEMPERATURE->{
                    temperature=event.values[0]
                }
                Sensor.TYPE_LIGHT->{
                    light=event.values[0]
                }
                Sensor.TYPE_PRESSURE->{
                    pressure=event.values[0]
                }
                Sensor.TYPE_RELATIVE_HUMIDITY->{
                    humidity=event.values[0]
                }
            }
        }
        mySensorListener.listenToSensorValues(this.accelerometerX, this.accelerometerY, this.accelerometerZ,
            this.gyroscopeX, this.gyroscopeY, this.gyroscopeZ,
            this.magneticX, this.magneticY, this.magneticZ,
            this.temperature, this.pressure, this.humidity, this.light)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        print("Change")
    }
    fun stopUpdates()
    {
        sm.unregisterListener(this)
    }
    companion object {
        interface SensorManagerInterface
        {
            fun listenToSensorValues(accelerometerX:Float, accelerometerY:Float, accelerometerZ: Float,
                                     gyroscopeX:Float, gyroscopeY:Float, gyroscopeZ: Float,
                                     magneticX:Float, magneticY:Float, magneticZ: Float,
                                     temperature:Float, pressure:Float, humidity:Float, light:Float)
        }
    }
}