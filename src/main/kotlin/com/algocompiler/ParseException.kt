package com.algocompiler

class ParseException(message: String, val line: Int, val token: Token? = null) : RuntimeException(message) {
    override fun toString(): String {
        val tokenInfo = token?.value ?: "?"
        return "ParseException: $message (ligne $line, token='$tokenInfo')"
    }
}

