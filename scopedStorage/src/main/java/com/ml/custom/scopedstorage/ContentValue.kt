package com.ml.custom.scopedstorage


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentValue(val value:String)