import tornadofx.*
import kotlin.properties.Delegates

/*
 * Name        : "Pace Perfect"
 * Project     : Timing_App_Logic
 * File        : Timing.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

fun main() {
    val t = Timing()
    t.goalDistance = 5.0
    t.selectedDistanceUnit = "Kilometers"
    t.goalMinute = 20
    t.selectedSplitType = "Distance"
    t.splitNum = 1.0
    t.selectedSplitUnit = "Miles"
    t.calculateSplits()
}

class Splits {
    var unit: String by Delegates.observable("") {_, oldValue, newValue ->
        onUnitChanged?.invoke(oldValue, newValue)
    }
    private var onUnitChanged: ((String, String) -> Unit)? = null

    var distance: Double by Delegates.observable(0.0){ _, oldValue, newValue ->
        onDistanceChanged?.invoke(oldValue, newValue)
    }
    private var onDistanceChanged: ((Double, Double) -> Unit)? = null

    var hour: Int by Delegates.observable(0){ _, oldValue, newValue ->
        onHourChanged?.invoke(oldValue, newValue)
    }
    private var onHourChanged: ((Int, Int) -> Unit)? = null

    var minute: Int by Delegates.observable(0){ _, oldValue, newValue ->
        onMinuteChanged?.invoke(oldValue, newValue)
    }
    private var onMinuteChanged: ((Int, Int) -> Unit)? = null

    var second: Double by Delegates.observable(0.0){ _, oldValue, newValue ->
        onSecondChanged?.invoke(oldValue, newValue)
    }
    private var onSecondChanged: ((Double, Double) -> Unit)? = null
}

class Timing {

    // Converted JAVA Logic
    // set global variables to be used by all functions
    private var totalDistance = 0.0                       //total distance
    var goalDistance = 0.0                       //goal distance
    private var totalTimeS = 0.0                       //in total secs
    private var totalPaceS = 0.0                         //in total secs
    var goalHour = 0                         //cd ..4hr component for time
    var goalMinute = 0                         //min component for time
    var goalSecond = 0.0                       //sec component for time
    private var paceHour = 0                         //hr component for pace
    private var paceMinute = 0                         //min component for pace
    private var paceSecond = 0.0                         //sec component for pace
    val marathonDistance = "26.21875"                //marathon distance in miles
    val halfMarathonDistance = "13.109375"               //half marathon distance in miles
    var splitList = mutableListOf<Splits>().observable()
    var selectedDistanceUnit = ""
    var selectedSplitType = ""
    var splitNum = 0.0
    var selectedSplitUnit = ""

    fun calculateSplits() {
        goalTimeToSeconds()
        convertDistanceType()
        calculatePace()
    }

    fun reset(){
        totalDistance = 0.0                       //total distance
        goalDistance = 0.0                       //goal distance
        totalTimeS = 0.0                       //in total secs
        totalPaceS = 0.0                         //in total secs
        goalHour = 0                         //cd ..4hr component for time
        goalMinute = 0                         //min component for time
        goalSecond = 0.0                       //sec component for time
        paceHour = 0                         //hr component for pace
        paceMinute = 0                         //min component for pace
        paceSecond = 0.0                         //sec component for pace
        splitList.clear()
        selectedDistanceUnit = ""
        selectedSplitType = ""
        splitNum = 0.0
        selectedSplitUnit = ""
    }

    private fun goalTimeToSeconds() {
        totalTimeS += goalHour * 3600
        totalTimeS += goalMinute * 60
        totalTimeS += goalSecond
    }

    private fun convertDistanceType() {
        if (selectedDistanceUnit == "Miles" && selectedSplitUnit == "Miles"
            && selectedSplitType == "Distance"
        ) {
            //todo remove if not used
        } else if (selectedDistanceUnit == "Miles" && selectedSplitUnit == "Kilometers"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 1.60934
        } else if (selectedDistanceUnit == "Miles" && selectedSplitUnit == "Meters"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 1609.34
        } else if (selectedDistanceUnit == "Kilometers" && selectedSplitUnit == "Miles"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 0.621371
        } else if (selectedDistanceUnit == "Kilometers" && selectedSplitUnit == "Kilometers"
            && selectedSplitType == "Distance"
        ) {
            //todo remove if not used
        } else if (selectedDistanceUnit == "Kilometers" && selectedSplitUnit == "Meters"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 1000
        } else if (selectedDistanceUnit == "Meters" && selectedSplitUnit == "Miles"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 0.000621371
        } else if (selectedDistanceUnit == "Meters" && selectedSplitUnit == "Kilometers"
            && selectedSplitType == "Distance"
        ) {
            goalDistance *= 0.001
        } else if (selectedDistanceUnit == "Meters" && selectedSplitUnit == "Meters"
            && selectedSplitType == "Distance"
        ) {
            //todo remove if not used
        }
    }

    private fun calculatePace() {
        do {
            totalPaceS = totalTimeS
            totalPaceS /= goalDistance
            if (goalDistance >= splitNum) {
                totalDistance += splitNum
                goalDistance -= splitNum
                totalPaceS *= splitNum
                totalTimeS -= totalPaceS
                paceSecond += totalPaceS % 60
                if (paceSecond >= 60) {
                    paceSecond %= 60
                    paceMinute++
                }
                totalPaceS /= 60
                paceMinute += (totalPaceS % 60).toInt()
                if (paceMinute >= 60) {
                    paceMinute %= 60
                    paceHour++
                }
                totalPaceS /= 60
                paceHour += totalPaceS.toInt()
            } else {
                totalDistance += goalDistance
                goalDistance -= splitNum
                paceSecond += totalPaceS % 60
                if (paceSecond >= 60) {
                    paceSecond %= 60
                    paceMinute++
                }
                totalTimeS /= 60
                paceMinute += (totalTimeS % 60).toInt()
                if (paceMinute >= 60) {
                    paceMinute %= 60
                    paceHour++
                }
                totalTimeS /= 60
                paceHour += totalTimeS.toInt()
            }
            val splits = Splits()
            splits.unit = selectedSplitUnit
            splits.distance = totalDistance
            splits.hour = paceHour
            splits.minute = paceMinute
            splits.second = paceSecond
            splitList.add(splits)
            //println("$selectedSplitUnit $totalDistance: $paceHour:$paceMinute:$paceSecond") //todo

        } while (goalDistance > 0)
    }
}