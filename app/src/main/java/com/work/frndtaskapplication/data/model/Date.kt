package com.work.frndtaskapplication.data.model

import java.io.Serializable

data class Date(
    var month: Int,
    var year: Int,
    var day: String = "1"
) : Serializable