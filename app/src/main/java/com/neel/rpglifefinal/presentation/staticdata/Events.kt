package com.neel.rpglifefinal.presentation.staticdata

data class Events(val name:String,val description:String,val typeEvent: Stats)

var eventList = arrayListOf(
    Events("Yes","Very nice.",Stats.STR)
)