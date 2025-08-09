package org.example.integer

import org.example.common.Detector
import org.example.common.tokenize


class IntegerDetector : Detector {
    override fun detect(input: String): Boolean {
        if (input.isEmpty()) return false
        var state: IntegerState = IntegerStart
        for (t in tokenize(input)) {
            state = state.next(t)
            if (state === IntegerInvalid) return false
        }
        return state.isAccepting()
    }
}
