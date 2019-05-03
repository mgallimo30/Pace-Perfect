/*
 * Name        : "Pace Perfect"
 * Project     : Timing_App_Logic
 * File        : Timing.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

class timing {

    // Converted JAVA Logic
    // set global variables to be used by all functions
    val total_distance        = 0.0                       //total distance
    val total_time_s          = 0                         //in total secs
    val total_pace_s          = 0                         //in total secs
    val time_hour             = 0                         //cd ..4hr component for time
    val time_minute           = 0                         //min component for time
    val time_second           = 0                         //sec component for time
    val pace_hour             = 0                         //hr component for pace
    val pace_minute           = 0                         //min component for pace
    val pace_second           = 0                         //sec component for pace
    val marathon_distance     = "26.21875"                //marathon distance in miles
    val half_marathon_distance= "13.109375"               //half marathon distance in miles

    enum class pace_unit {                      //unit pace is in (the per unit, aka mile, kilometer, quarter, half, etc)
        mile, kilometer, meter
    }
    enum class distance_unit {                  //type of unit dist is miles or kilometers
        Miles,
        Kilometers
    }
    enum class event {                          //event distance like marathon, half-marathon
        Marathon,
        Halfmarathon,
        FiveK,
        FiveM,
        EightK,
        TenK,
        FifteenK,
        TenM,
        TwentyK,
        FifteenM,
        TwentyfiveK,
        ThirtyK,
        TwentyM
    }

}