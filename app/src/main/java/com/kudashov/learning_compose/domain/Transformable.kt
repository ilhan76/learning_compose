package com.kudashov.learning_compose.domain

interface Transformable<T> {

    fun transform(): T
}

fun <T> List<Transformable<T>>.transform() = map {
    it.transform()
}