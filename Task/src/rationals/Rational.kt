package rationals

import java.math.BigInteger
import java.math.RoundingMode

data class Rational(val numerator: BigInteger, val denominator: BigInteger = 1.toBigInteger()) : Comparable<Rational> {

    override fun toString(): String {
        val normalizedRational = this.normalize()
        return if (normalizedRational.denominator == 1.toBigInteger()) {
            normalizedRational.numerator.toString()
        } else {
            "${normalizedRational.numerator}/${normalizedRational.denominator}"
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Rational && this.compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return this.toBigDecimal().hashCode()
    }

    operator fun times(other: Rational): Rational {
        return Rational(numerator * other.numerator, denominator * other.denominator)
    }

    operator fun div(other: Rational): Rational {
        return Rational(numerator * other.denominator, denominator * other.numerator)
    }

    operator fun plus(other: Rational): Rational {
        return Rational((numerator * other.denominator) + (other.numerator * denominator), denominator * other.denominator)
    }

    operator fun minus(other: Rational): Rational {
        return Rational((numerator * other.denominator) - (other.numerator * denominator), denominator * other.denominator)
    }

    override operator fun compareTo(other: Rational): Int {
        return when {
            toBigDecimal() > other.toBigDecimal() -> 1
            toBigDecimal() == other.toBigDecimal() -> 0
            else -> -1
        }
    }

    private fun toBigDecimal() = this.numerator.toBigDecimal().divide(this.denominator.toBigDecimal(), 10, RoundingMode.HALF_UP)

    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator)
    }

    operator fun rangeTo(other: Rational): ClosedRange<Rational> {
        return RationalRange(this, other)
    }

}

fun Rational.normalize(): Rational {
    val divisor = greatestCommonFactor(numerator, denominator)
    return if (divisor == 0.toBigInteger())
        this
    else if (numerator < 0.toBigInteger() && denominator < 0.toBigInteger()) {
        Rational(numerator.abs() / divisor, denominator.abs() / divisor)
    } else if (numerator > 0.toBigInteger() && denominator < 0.toBigInteger()) {
        Rational(numerator.abs() / divisor, denominator.abs() / divisor).unaryMinus()
    } else
        Rational(numerator / divisor, denominator / divisor)
}

fun greatestCommonFactor(numerator: BigInteger, denominator: BigInteger): BigInteger {
    val x = numerator.abs()
    val y = denominator.abs()
    return if (x % y == 0.toBigInteger()) {
        y
    } else {
        greatestCommonFactor(y, (x % y))
    }
}

infix fun Int.divBy(denominator: Int): Rational {
    if (denominator != 0) {
        return Rational(this.toBigInteger(), denominator.toBigInteger())
    } else {
        throw Exception("You cannot have 0 as denominator")
    }
}

infix fun Long.divBy(denominator: Long): Rational {
    if (denominator != 0L) {
        return Rational(this.toBigInteger(), denominator.toBigInteger())
    } else {
        throw Exception("You cannot have 0 as denominator")
    }
}

infix fun BigInteger.divBy(denominator: BigInteger): Rational {
    if (denominator != 0.toBigInteger()) {
        return Rational(this, denominator)
    } else {
        throw Exception("You cannot have 0 as denominator")
    }
}

fun String.toRational(): Rational {
    return if (this.contains('/')) {
        if (this.substringAfter('/') == "0") {
            throw Exception("You cannot have 0 as denominator")
        } else {
            val newRational = Rational(substringBefore('/').toBigInteger(), substringAfter('/').toBigInteger())
            newRational.normalize()
        }
    } else {
        Rational(this.toBigInteger())
    }
}

class RationalRange(override val start: Rational, override val endInclusive: Rational) : ClosedRange<Rational>


class BigIntegerProgression(override val start: BigInteger, override val endInclusive: BigInteger, private val step: BigInteger = 1.toBigInteger()) : Iterable<BigInteger>, ClosedRange<BigInteger> {
    override fun iterator(): Iterator<BigInteger> {
        return BigIntegerIterator(start, endInclusive, step)
    }
}

class BigIntegerIterator(private val start: BigInteger, private val endInclusive: BigInteger, private val step: BigInteger) : Iterator<BigInteger> {
    var current = start

    override fun hasNext(): Boolean {
        return if (endInclusive > start) {
            current <= endInclusive
        } else {
            current >= endInclusive
        }
    }

    override fun next(): BigInteger {
        val next = current
        current += step
        return next
    }
}

operator fun BigInteger.rangeTo(other: BigInteger) = BigIntegerProgression(this, other)

fun BigInteger.downTo(other: BigInteger): BigIntegerProgression {
    return BigIntegerProgression(this, other, (-1).toBigInteger())
}


fun main(args: Array<String>) {
    val r1 = 1 divBy 2
    val r2 = 2000000000L divBy 4000000000L
    println(r1 == r2)

    println((2 divBy 1).toString() == "2")

    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    println("1/2".toRational() - "1/3".toRational() == "1/6".toRational())
    println("1/2".toRational() + "1/3".toRational() == "5/6".toRational())

    println(-(1 divBy 2) == (-1 divBy 2))

    println((1 divBy 2) * (1 divBy 3) == "1/6".toRational())
    println((1 divBy 2) / (1 divBy 4) == "2".toRational())

    println((1 divBy 2) < (2 divBy 3))
    println((1 divBy 2) in (1 divBy 3)..(2 divBy 3))

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}