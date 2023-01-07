package com.example.common

enum class HttpCode(val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    UN_AUTHORIZED(401),
    NOT_FOUND(404)
}