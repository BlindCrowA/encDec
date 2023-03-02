package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {
    val action = if (args.contains("-mode")) args[args.indexOf("-mode") + 1] else "enc"
    val key = if (args.contains("-key")) args[args.indexOf("-key") + 1].toInt() else 0
    val string = if (args.contains("-data")) args[args.indexOf("-data") + 1]
    else if (args.contains("-in")) {
        val file = File(args[args.indexOf("-in") + 1])
        if (file.exists()) file.readText()
        else {
            println("Error! The specified file cannot be found")
            return
        }
    } else ""
    val alg = if(args.contains("-alg")) args[args.indexOf("-alg")+1] else "shift"
    val value = if (action == "enc") string.map { encrypt(it, key,alg) }.joinToString("")
    else string.map { decrypt(it, key,alg) }.joinToString("")

    if (args.contains("-out")) File(args[args.indexOf("-out") + 1]).writeText(value)
    else println(value)
}

fun encrypt(char: Char, key: Int, alg: String): Char {
    return when (alg) {
        "unicode" -> char + key
        "shift" -> if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A'.code else 'a'.code
            (((char.code - base + key) % 26 + 26) % 26 + base).toChar()
        } else {
            char
        }
        else -> ' '
    }
}

fun decrypt(char: Char, key: Int, alg: String): Char {
    return when (alg) {
        "unicode" -> char - key
        "shift" -> if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A'.code else 'a'.code
            (((char.code - base - key) % 26 + 26) % 26 + base).toChar()
        } else {
            char
        }
        else -> ' '
    }
}