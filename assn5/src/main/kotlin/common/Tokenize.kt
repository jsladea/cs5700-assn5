package org.example.common

fun tokenize(input: String): List<String> {
    val out = ArrayList<String>(input.length)
    for (i in input.indices) out += input.substring(i, i + 1)
    return out
}
