// !LANGUAGE: +MultiPlatformProjects
// !USE_EXPERIMENTAL: kotlin.ExperimentalMultiplatform
// IGNORE_BACKEND: NATIVE
// FULL_JDK
// FILE: A.kt
package a

@OptionalExpectation
expect annotation class A(val x: Int)

// FILE: B.kt
import a.A
import java.lang.reflect.Modifier

class B {
    @A(42)
    fun test() {}
}

fun box(): String {
    val annotations = B::class.java.declaredMethods.single().annotations
    if (annotations.isNotEmpty()) return "Fail 1: ${annotations.toList()}"

    // Can't use A::class.java because "Declaration annotated with '@OptionalExpectation' can only be used inside an annotation entry"
    if (Modifier.isPublic(Class.forName("a.A").modifiers)) return "Fail 2: optional annotation class should not be public in the bytecode"

    return "OK"
}
