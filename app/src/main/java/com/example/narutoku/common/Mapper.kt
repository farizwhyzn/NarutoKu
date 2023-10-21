package com.example.narutoku.common

interface Mapper<F, T> {
    fun mapApiModelToModel(from: F): T
}