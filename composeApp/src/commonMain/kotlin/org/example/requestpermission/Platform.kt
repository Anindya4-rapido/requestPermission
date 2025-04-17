package org.example.requestpermission

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform