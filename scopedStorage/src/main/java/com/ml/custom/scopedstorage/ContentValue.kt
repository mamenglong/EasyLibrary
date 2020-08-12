package com.ml.custom.scopedstorage

import java.lang.annotation.ElementType

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentValue(val value:String)