package org.lzh.shipan.daluren


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME) //这一行也可以省略
annotation class ShenShaAndGuaTi(val type: String)