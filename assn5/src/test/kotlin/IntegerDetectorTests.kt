import org.example.integer.IntegerDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IntegerDetectorTests {
    private val d = IntegerDetector()

    @ParameterizedTest
    @ValueSource(strings = ["1", "9", "123", "9876543210"])
    fun validIntegers(s: String) = assertEquals(true, d.detect(s))

    @ParameterizedTest
    @ValueSource(strings = ["", "0", "0123", "1a", "a1", " 1", "1 "])
    fun invalidIntegers(s: String) = assertEquals(false, d.detect(s))

    @Test
    fun invalidMidStream() {
        // Valid start, then fail on nondigit
        assertEquals(false, d.detect("12a3"))
    }
}
