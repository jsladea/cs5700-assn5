package org.example.binary

import org.example.common.Detector
import org.example.common.Token
import org.example.common.tokenize

class BinaryDetector : Detector {
    override fun detect(input: String): Boolean {
        if (input.isEmpty()) return false
        var state: BinaryState = BinStart
        for (t in tokenize(input)) {
            if (!Token.isBinary(t)) return false
            state = state.next(t)
            if (state === BinInvalid) return false
        }
        return state === BinOne // ends with '1'
    }
}
