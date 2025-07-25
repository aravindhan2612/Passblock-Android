package com.ab.an.core.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Represents a successful operation with data.
     */
    class Success<T>(data: T?) : Resource<T>(data)

    /**
     * Represents an error state, potentially with an error message.
     */
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state, indicating that an operation is in progress.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}