package com.greener.domain.model

import java.awt.Color

enum class ActionTodo(val actionId: Int, val stringName: String, val color: String) {
    COMPLETE_ALL(0, "complete_all", "blue100"),
    WATERING(1, "watering", "blue100"),
    RE_POT(2, "repot", "blue100"),
    PRUNING(3, "pruning", "blue100"),
    NUTRITION(4, "nutrition", "blue100"),
    VENTILATION(5, "ventilation", "blue100")
}