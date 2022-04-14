package com.gargour.warehouse.domain.use_case.registration

class GenerateRegistration {
    operator fun invoke(imeiString: String, serial: String): String? {
        if (serial.isEmpty())
            return null
        val serialCharArray = imeiString.toCharArray()
        var serialAscii = 0
        for (c in serialCharArray) serialAscii += c.code
        var partNumberAscii = 0
        for (c in serial) partNumberAscii += c.code
        val asciiMulti = (serialAscii * partNumberAscii).toLong()
        val asciiMultiCharArray = asciiMulti.toString().toCharArray()
        val result = StringBuilder()
        for (j in asciiMultiCharArray.size - 1 downTo 0)
            result.append(asciiMultiCharArray[j])
        return (result.toString())
    }

}