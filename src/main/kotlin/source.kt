/*
 * Name        : "Pace Perfect"
 * Project     : Timing_App_Logic
 * File        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

package com.example.app.myapp

import Splits
import Timing
import javafx.beans.property.*
import javafx.scene.paint.Color
import tornadofx.*

val t = Timing()

class MyApp : App(MyView::class)

class MyView : View("Pace Perfect") {
    private val controller: MyController by inject()
    private var selectedEvent = SimpleStringProperty()
    private val goalDistanceField = SimpleDoubleProperty()
    private var selectedDistance = SimpleStringProperty()
    private val goalHourField = SimpleIntegerProperty()
    private val goalMinuetField = SimpleIntegerProperty()
    private val goalSecondField = SimpleDoubleProperty()
    private var selectedSplitType = SimpleStringProperty()
    private val splitValue = SimpleDoubleProperty()
    private var selectedSplitDistance = SimpleStringProperty()
    private var error = false
    private val distanceUnit = observableList(
        "Miles",
        "Kilometers",
        "Meters"
    )
    private val splits = observableList(
        //"Time",  //todo
        "Distance"
    )
    private val event = observableList(
        "",
        "Marathon",
        "Half Marathon",
        "5K",
        "5 Miles",
        "8K",
        "10K",
        "15K",
        "10 Miles",
        "20K",
        "15 Miles",
        "25K",
        "30K",
        "20 Miles"
    )

    override val root = borderpane {
        setPrefSize(414.0, 736.0)
        center = form {
            useMaxWidth = true
            style {
                backgroundColor += Color.BLACK
            }


        }
    }

    init {
        with(root.center)
        {
            vbox {
                label("Please enter goal distance, goal time, and splits") {
                    textFill = Color.WHITE
                    style {
                        fontSize = 18.px
                    }
                }
                label("Select an event or enter goal distance") {
                    textFill = Color.WHITE
                    style {
                        fontSize = 15.px
                    }
                }
                label("Event ") {
                    textFill = Color.WHITE
                }
                combobox(selectedEvent, event)
                label("Goal Distance") {
                    textFill = Color.WHITE
                }
                hbox {
                    textfield(goalDistanceField)
                    combobox(selectedDistance, distanceUnit)
                }
                label("Please enter goal time") {
                    textFill = Color.WHITE
                    style {
                        fontSize = 18.px
                    }
                }
                label("Hours") {
                    textFill = Color.WHITE
                }
                textfield(goalHourField)
                label("Minutes:") {
                    textFill = Color.WHITE
                }
                textfield(goalMinuetField)
                label("Seconds:") {
                    textFill = Color.WHITE
                }
                textfield(goalSecondField)
                label("Split:") {
                    textFill = Color.WHITE
                }
                hbox {
                    combobox(selectedSplitType, splits)
                    textfield(splitValue)
                    combobox(selectedSplitDistance, distanceUnit)
                }
                label()
                label("Hit save to show splits or reset to clear form") {
                    textFill = Color.WHITE
                    style {
                        fontSize = 18.px
                    }
                }
                hbox {
                    button("Save") {
                        useMaxWidth = true
                        action {
                            if (goalDistanceField.value != 0.0
                                && selectedDistance.value != null
                                && (goalHourField.value != 0
                                        || goalMinuetField.value != 0
                                        || goalSecondField.value != 0.0)
                                && selectedSplitType.value != null
                                && splitValue.value != 0.0
                                && selectedSplitDistance.value != null
                            ) {
                                controller.storeData(
                                    goalDistanceField.value, goalHourField.value, goalMinuetField.value,
                                    goalSecondField.value, splitValue.value
                                )
                                controller.calcSplits()
                                selectedEvent.value = null
                                goalDistanceField.value = 0.0
                                selectedDistance.value = null
                                goalHourField.value = 0
                                goalMinuetField.value = 0
                                goalSecondField.value = 0.0
                                selectedSplitType.value = null
                                splitValue.value = 0.0
                                selectedSplitDistance.value = null
                                replaceWith<Watch>()
                            } else {
                                error = true
                            }
                        }
                    }
                    button("Reset") {
                        useMaxWidth = true
                        action {
                            selectedEvent.value = null
                            goalDistanceField.value = 0.0
                            selectedDistance.value = null
                            goalHourField.value = 0
                            goalMinuetField.value = 0
                            goalSecondField.value = 0.0
                            selectedSplitType.value = null
                            splitValue.value = 0.0
                            selectedSplitDistance.value = null
                        }
                    }

                }
            }
        }
        selectedEvent.onChange {
            when (it) {
                "Marathon" -> {
                    goalDistanceField.value = t.marathonDistance.toDouble()
                    selectedDistance.value = "Miles"
                }
                "Half Marathon" -> {
                    goalDistanceField.value = t.halfMarathonDistance.toDouble()
                    selectedDistance.value = "Miles"
                }
                "5K" -> {
                    goalDistanceField.value = 5.0
                    selectedDistance.value = "Kilometers"
                }
                "5 Miles" -> {
                    goalDistanceField.value = 5.0
                    selectedDistance.value = "Miles"
                }
                "8K" -> {
                    goalDistanceField.value = 5.0
                    selectedDistance.value = "Kilometers"
                }
                "10K" -> {
                    goalDistanceField.value = 10.0
                    selectedDistance.value = "Kilometers"
                }
                "15K" -> {
                    goalDistanceField.value = 15.0
                    selectedDistance.value = "Kilometers"
                }
                "10 Miles" -> {
                    goalDistanceField.value = 10.0
                    selectedDistance.value = "Miles"
                }
                "20K" -> {
                    goalDistanceField.value = 20.0
                    selectedDistance.value = "Kilometers"
                }
                "15 Miles" -> {
                    goalDistanceField.value = 15.0
                    selectedDistance.value = "Miles"
                }
                "25K" -> {
                    goalDistanceField.value = 25.0
                    selectedDistance.value = "Kilometers"
                }
                "30K" -> {
                    goalDistanceField.value = 30.0
                    selectedDistance.value = "Kilometers"
                }
                "20 Miles" -> {
                    goalDistanceField.value = 20.0
                    selectedDistance.value = "Miles"
                }
            }
        }

        selectedDistance.onChange {
            if (it != null) {
                controller.storeDistanceUnit(it)
            }
        }

        selectedSplitType.onChange {
            if (it != null) {
                controller.storeSelectedSplitType(it)
            }
        }

        selectedSplitDistance.onChange {
            if (it != null) {
                controller.storeSelectedSplitUnit(it)
            }
        }
    }

}

class Watch : View("Watch") {
    private val controller: MyController by inject()
    override val root = borderpane {
        bottom = hbox {
            button("Reset") {
                useMaxWidth = true
                action {
                    controller.clearData()
                    replaceWith<MyView>()
                }
            }
        }
        center = tableview(t.splitList) {
            column("Unit", Splits::unit)
            column("Distance", Splits::distance)
            column("Hour", Splits::hour)
            column("Minute", Splits::minute)
            column("Second", Splits::second)
        }
    }
}

class MyController : Controller() {
    fun storeData(goalD: Double, goalH: Int, goalM: Int, goalS: Double, split: Double) {
        t.goalDistance = goalD
        t.goalHour = goalH
        t.goalMinute = goalM
        t.goalSecond = goalS
        t.splitNum = split
    }

    fun storeDistanceUnit(unit: String) {
        t.selectedDistanceUnit = unit
    }

    fun storeSelectedSplitType(unit: String) {
        t.selectedSplitType = unit
    }

    fun storeSelectedSplitUnit(unit: String) {
        t.selectedSplitUnit = unit
    }

    fun calcSplits() {
        t.calculateSplits()
    }
    fun clearData() {
        t.reset()
    }
}