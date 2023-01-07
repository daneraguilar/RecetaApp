package com.example.common

sealed class Result<out Success, out Failure> {
    data class Success<out Success>(val value: Success) : Result<Success, Nothing>()
    data class Failure<out Failure>(val reason: Failure) : Result<Nothing, Failure>()
}

sealed class ResultUseCaseError {
    data class UnKnowError(val message: String) : ResultUseCaseError()
    object NetworkError : ResultUseCaseError()
}

sealed class ResultRepositoryError {
    object NetworkError : ResultRepositoryError()
    object UnAuthorized : ResultRepositoryError()
    data class BadRequest(val message: String) : ResultRepositoryError()
    data class UnKnowError(val message: String) : ResultRepositoryError()
    object ParserError : ResultRepositoryError()
}