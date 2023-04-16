annotation class ValueContainer

@ValueContainer
class FreezableBox<T>(var value: T) {

    private var frozen = false

    fun assign(value: T) {
        check(!frozen) { "You can't change value of the frozen box" }
        this.value = value
    }

    fun freeze() {
        frozen = true
    }
}

class Config(
    val name: FreezableBox<String>,
) {
    fun finalize() {
        name.freeze()
    }
}

fun main() {
    val config = Config(FreezableBox("foo"))
    config.name = "bar"

    config.finalize()   // Deny further config changes
    config.name = "buz" // Throws IllegalStateException
}
