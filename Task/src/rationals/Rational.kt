package rationals

import java.math.BigInteger
import java.math.RoundingMode

data class Rational(private val _numeratorAndDenominator: Pair<BigInteger, BigInteger>) : Comparable<Rational> {
    constructor(numerator: BigInteger, denominator: BigInteger = 1.toBigInteger()) : this(normalize(numerator, denominator))

    private val numerator = _numeratorAndDenominator.first
    val denominator = _numeratorAndDenominator.second

    override fun toString(): String {
        return if (denominator == 1.toBigInteger()) {
            numerator.toString()
        } else {
            "$numerator/$denominator"
        }
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

private fun normalize(numerator: BigInteger, denominator: BigInteger): Pair<BigInteger, BigInteger> {
    fun greatestCommonFactor(): BigInteger {
        val minOf = minOf(numerator.abs(), denominator.abs())
        return if (numerator % minOf == 0.toBigInteger() && denominator % minOf == 0.toBigInteger()) {
            minOf
        } else {
            ((minOf/2.toBigInteger()).downTo(1.toBigInteger())).first { numerator % it == 0.toBigInteger() && denominator % it == 0.toBigInteger() }
        }
    }
    val divisor = greatestCommonFactor()
    return if (divisor == 0.toBigInteger())
        Pair(numerator, denominator)
    else
        Pair(numerator / divisor, denominator / divisor)
}

infix fun Int.divBy(denominator: Int): Rational {
    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

infix fun Long.divBy(denominator: Long): Rational {
    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

infix fun BigInteger.divBy(denominator: BigInteger): Rational {
    return Rational(this, denominator)
}

internal fun String.toRational(): Rational {
    return if (this.contains('/')) {
        Rational(substringBefore('/').toBigInteger(), substringAfter('/').toBigInteger())
    } else {
        Rational(this.toBigInteger())
    }
}

internal fun BigInteger.findFactors(): List<BigInteger> {
    return (1.toBigInteger()..this).filter { this % it == 0.toBigInteger() }
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