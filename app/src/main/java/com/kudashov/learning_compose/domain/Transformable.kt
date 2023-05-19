package com.kudashov.learning_compose.domain

interface Transformable<T> {

    fun transform(): T
}