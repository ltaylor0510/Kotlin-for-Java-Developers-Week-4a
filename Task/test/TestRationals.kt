package rationals

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestRationals {
    @Test
    fun test0() {
        val r1 = 1 divBy 2
        val r2 = 2000000000L divBy 4000000000L
        assertEquals(true, r1 == r2, """Wrong result for
            |val r1 = 1 divBy 2
            |val r2 = 2000000000L divBy 4000000000L
            |r1 == r2""".trimMargin())
    }

    @Test
    fun test1() {
        assertEquals(true, (2 divBy 1).toString() == "2", "Wrong result for\n(2 divBy 1).toString() == \"2\"")
    }

    @Test
    fun test1b() {
        assertEquals(true, (3 divBy 1).toString() == "3", "Wrong result for\n(3 divBy 1).toString() == \"2\"")
    }

    @Test
    fun test1c() {
        assertEquals(true, (1 divBy 2).toString() == "1/2", "Wrong result for\n(1 divBy 2).toString() == \"1/2\"")
    }

    @Test
    fun test1d() {
        assertEquals("1/2", (2 divBy 4).toString(), "Wrong result for\n(2 divBy 4).toString() == \"1/2\"")
    }

    @Test
    fun testingFindFactors() {
        assertEquals(listOf(1.toBigInteger(), 2.toBigInteger(), 4.toBigInteger(), 8.toBigInteger()), 8.toBigInteger().findFactors())
    }

    @Test
    fun test2() {
        assertEquals("-1/2", (-2 divBy 4).toString(), "Wrong result for\n(-2 divBy 4).toString() == \"-1/2\"")
    }

    @Test
    fun test3a() {
        assertEquals("1/2", "1/2".toRational().toString())
    }

    @Test
    fun test3b() {
        assertEquals("23", "23".toRational().toString())
        assertEquals(1.toBigInteger(), "23".toRational().denominator)
    }

    @Test
    fun test3() {
        assertEquals(true, "117/1098".toRational().toString() == "13/122", "Wrong result for\n\"117/1098\".toRational().toString() == \"13/122\"")
    }

    @Test
    fun multiplyingTwoRationals() {
        val rational1 = 1 divBy 2
        val rational2 = 1 divBy 3
        assertEquals("1/6", (rational1 * rational2).toString())

    }

    @Test
    fun divideTwoRationals() {
        val rational1 = 1 divBy 2
        val rational2 = 1 divBy 3
        assertEquals("3/2", (rational1 / rational2).toString())
    }

    @Test
    fun addTwoRationals() {
        val rational1 = 1 divBy 2
        val rational2 = 1 divBy 3
        assertEquals("5/6", (rational1 + rational2).toString())
    }

    @Test
    fun subtractTwoRationals() {
        val rational1 = 1 divBy 2
        val rational2 = 1 divBy 3
        assertEquals("1/6", (rational1 - rational2).toString())
    }

    @Test
    fun test4() {
        assertEquals(true, "1/2".toRational() - "1/3".toRational() == "1/6".toRational(), "Wrong result for\n\"1/2\".toRational() - \"1/3\".toRational() == \"1/6\".toRational()")
        assertEquals(true, "1/2".toRational() + "1/3".toRational() == "5/6".toRational(), "Wrong result for\n\"1/2\".toRational() + \"1/3\".toRational() == \"5/6\".toRational()")
    }

    @Test
    fun test5() {
        assertEquals(true, -(1 divBy 2) == (-1 divBy 2), "Wrong result for\n-(1 divBy 2) == (-1 divBy 2)")
    }

    @Test
    fun test6() {
        assertEquals(true, (1 divBy 2) * (1 divBy 3) == "1/6".toRational(), "Wrong result for\n(1 divBy 2) * (1 divBy 3) == \"1/6\".toRational()")
        assertEquals("2".toRational(), (1 divBy 2) / (1 divBy 4), "Wrong result for\n(1 divBy 2) / (1 divBy 4) == \"2\".toRational()")
    }

    @Test
    fun test7() {
        assertEquals(true, (1 divBy 2) < (2 divBy 3), "Wrong result for\n(1 divBy 2) < (2 divBy 3)")
        assertEquals(true, (3 divBy 9) < (1 divBy 2), "Wrong result for\n(2 divBy 9) < (1 divBy 2)")
        assertEquals(true, (1 divBy 2) in (1 divBy 3)..(2 divBy 3), "Wrong result for\n(1 divBy 2) in (1 divBy 3)..(2 divBy 3)")
    }

    @Test
    fun test8() {
        assertEquals(true, "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2, "Wrong result for\n" +
                "\"912016490186296920119201192141970416029\".toBigInteger() divBy " +
                "\"1824032980372593840238402384283940832058\".toBigInteger() == 1 divBy 2")
    }

//    @Test
//    fun testBigInteger() {
//        assertEquals(true, ( "982451653".toBigInteger() divBy "982451654".toBigInteger()).toString() == "982451653/982451654", "Wrong result for\n(2 divBy 4).toString() == \"1/2\"")
//    }

    @Test
    fun `BigInteger range creates ascending range`() {
        assertEquals(listOf(1.toBigInteger(), 2.toBigInteger(), 3.toBigInteger(), 4.toBigInteger()), (1.toBigInteger()..4.toBigInteger()).map { it })
    }

    @Test
    fun `BigInteger range creates descending range`() {
        assertEquals(listOf(4.toBigInteger(), 3.toBigInteger(), 2.toBigInteger(), 1.toBigInteger()),
                (4.toBigInteger().downTo(1.toBigInteger()).map { it }))
    }

}