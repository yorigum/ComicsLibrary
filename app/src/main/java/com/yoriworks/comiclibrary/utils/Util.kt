package com.yoriworks.comiclibrary

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.math.BigInteger
import java.security.MessageDigest

fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
    val hashStr = timestamp + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(hashStr.toByteArray()))
        .toString(16)
        .padStart(32, padChar = '0')
}

@Composable
fun CharacterImage(
    url: String?, modifier: Modifier, contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = url, contentDescription = null, modifier = modifier, contentScale = contentScale
    )
    
}


@Preview(showBackground = true)
@Composable
fun AttributionText(text: String = "sdsda") {
    Text(
        text = text, modifier = Modifier.padding(start = 8.dp, top = 4.dp), fontSize = 12.sp
    )
}

fun List<String>.comicsToString() = this.joinToString(separator = ", ")

open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}