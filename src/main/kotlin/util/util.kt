package util

fun gcd(a: Int, b: Int): Int{
    return when {
        a < b -> gcd(b, a)
        b == 0 -> a
        else -> gcd(b, a % b)
    }
}