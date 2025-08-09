import org.example.email.EmailDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EmailDetectorTests {
    private val d = EmailDetector()

    @ParameterizedTest
    @ValueSource(strings = [
        "a@b.c",
        "jake.bowden@usu.edu",
        "{}*$.&$*(@*$%&.*&*",
        "x@yz.t",       // minimal lengths per part
        "___@a.b"
    ])
    fun validEmail(s: String) = assertEquals(true, d.detect(s))

    @ParameterizedTest
    @ValueSource(strings = [
        "",                // empty
        "@b.c",            // empty part1
        "a@b@c.com",       // 2 '@'
        "a.b@b.b.c",       // 2 '.' after '@'
        "jake bowden@usu.edu", // space
        "a@.c",            // empty part2
        "a@b.",            // empty part3
        "a@b",             // no dot after @
        "a@b.c.",          // extra dot at end
    ])
    fun invalidEmail(s: String) = assertEquals(false, d.detect(s))

    @Test
    fun emptyString() {
        assertEquals(false, d.detect(""))
    }

    @Test
    fun spaceAtStart() {
        assertEquals(false, d.detect(" a@b.c"))
    }

    @Test
    fun spaceInLocal() {
        assertEquals(false, d.detect("a b@c.d"))
    }

    @Test
    fun spaceInDomain() {
        assertEquals(false, d.detect("a@b c.d"))
    }

    @Test
    fun spaceInSuffix() {
        assertEquals(false, d.detect("a@b.c d"))
    }

    @Test
    fun atAtStart() {
        assertEquals(false, d.detect("@a.b"))
    }

    @Test
    fun dotAtStart() {
        assertEquals(true, d.detect(".a@b.c")) // Leading dot in part1 is allowed
    }

    @Test
    fun doubleAt() {
        assertEquals(false, d.detect("a@@b.c"))
    }

    @Test
    fun doubleDotAfterAt() {
        assertEquals(false, d.detect("a@b..c"))
    }

    @Test
    fun dotBeforeAt() {
        assertEquals(true, d.detect("a.b@c.d"))
    }

    @Test
    fun specialCharsInLocal() {
        assertEquals(true, d.detect("a!#\$%&*@b.c"))
    }

    @Test
    fun specialCharsInDomain() {
        assertEquals(true, d.detect("a@b!#\$%&*.c"))
    }

    @Test
    fun specialCharsInSuffix() {
        assertEquals(true, d.detect("a@b.c!#\$%&*"))
    }

    @Test
    fun onlyOneCharEachPart() {
        assertEquals(true, d.detect("x@y.z"))
    }

    @Test
    fun suffixLongerThanOneChar() {
        assertEquals(true, d.detect("a@b.com"))
    }

    @Test
    fun dotAtEnd() {
        assertEquals(false, d.detect("a@b.c."))
    }

    @Test
    fun atAtEnd() {
        assertEquals(false, d.detect("a@b.c@"))
    }

    @Test
    fun dotImmediatelyAfterAt() {
        assertEquals(false, d.detect("a@.c"))
    }

    @Test
    fun multipleDotsInLocal() {
        assertEquals(true, d.detect("a.b.c@d.e"))
    }

    @Test
    fun multipleDotsInSuffix() {
        assertEquals(false, d.detect("a@b.c.d"))
    }
}
