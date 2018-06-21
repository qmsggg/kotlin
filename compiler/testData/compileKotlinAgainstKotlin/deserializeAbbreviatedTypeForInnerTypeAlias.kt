// FILE: A.kt

package a

class TestObserver<T> {
    fun assertValue(valuePredicate: (T) -> Boolean) {}
    fun foo(x: T?) {}
}

class Single<T> {
    fun test(): TestObserver<T> = TestObserver()
}

class Employee

class Either<T>
// Inlining this "middle" alias makes compiler success.
typealias DomainEither<T> = Either<T>
typealias DomainSingle<T> = Single<DomainEither<T>>

fun provideDomainSingle(): DomainSingle<Employee> = Single()

// FILE: b.kt

import a.*

fun box(): String {
    val testObs = provideDomainSingle().test()
    testObs.assertValue { true }
    return "OK"
}