package com.example.yourlocker.Model


class Device constructor(id: String, type: String, name:String, state: Boolean) {

    var device_id: String? = null
    var device_type: String? = null
    var device_state: Boolean? = null
    var device_name: String? = null

    init {
        this.device_id = id
        this.device_type = type
        this.device_state = state
        this.device_name = name
    }





}