package mn.test.resource.modules

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(value = GeneralDTO.Success::class, name = "success"),
    JsonSubTypes.Type(value = GeneralDTO.Errors::class, name = "error"),
)
sealed class GeneralDTO<T>(
    open val value: T?,
    open val errors: Set<String>?,
) {
    data class Success<T>(
        override val value: T,
        override val errors: Set<String>?,
    ) : GeneralDTO<T>(value, errors)

    data class Errors<T>(
        override val value: T?,
        override val errors: Set<String>,
    ) : GeneralDTO<T>(value, errors)

    companion object {
        fun <T> success(value: T): GeneralDTO<T> = Success(value, null)

        fun <T> error(error: String): GeneralDTO<T> = Errors(null, setOf(error))

        fun <T> error(errors: Set<String>): GeneralDTO<T> = Errors(null, errors)
    }
}