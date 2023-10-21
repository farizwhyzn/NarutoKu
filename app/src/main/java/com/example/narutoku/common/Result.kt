package com.example.narutoku.common

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val message: String?) : Result<T>()
}
