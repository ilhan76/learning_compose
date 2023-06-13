package com.kudashov.learning_compose.base.domain.util

interface Transformable<T> {

    fun transform(): T
}

fun <T> List<Transformable<T>>.transform() = map {
    it.transform()
}