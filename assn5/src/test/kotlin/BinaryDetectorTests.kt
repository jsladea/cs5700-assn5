import org.example.binary.BinaryDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Test

class BinaryDetectorTests {
    private val d = BinaryDetector()

    @ParameterizedTest
    @ValueSource(strings = ["1", "11", "101", "111111", "10011010001"])
    fun validBinary(s: String) = assertEquals(true, d.detect(s))

    @ParameterizedTest
    @ValueSource(strings = ["", "0", "01", "10", "1000010", "1a1", " 1", "1 "])
    fun invalidBinary(s: String) = assertEquals(false, d.detect(s))

    @Test
    fun invalidMidStreamChar() {
        assertEquals(false, d.detect("11x1")) // invalid char after some good ones
    }
}
