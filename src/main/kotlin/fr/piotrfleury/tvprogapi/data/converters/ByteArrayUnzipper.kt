package fr.piotrfleury.tvprogapi.data.converters

import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.ZipInputStream

@Service
class ByteArrayUnzipper {

    @Throws(IOException::class)
    fun toUnzippedByteArray(zippedBytes: ByteArray): ByteArray {
        val zipInputStream = ZipInputStream(ByteArrayInputStream(zippedBytes))
        val buff = ByteArray(1024)
        if (zipInputStream.getNextEntry() != null) {
            val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
            var l: Int
            while ((zipInputStream.read(buff).also { l = it }) > 0) {
                outputStream.write(buff, 0, l)
            }
            return outputStream.toByteArray()
        }
        return ByteArray(0)
    }
}