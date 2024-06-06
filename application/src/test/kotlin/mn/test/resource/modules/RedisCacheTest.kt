package mn.test.resource.modules

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.Cacheable
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.micronaut.http.annotation.QueryValue
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Singleton
import java.util.concurrent.atomic.AtomicInteger

@MicronautTest
@Property(name = "spec.name", value = "RedisCacheTest")
open class RedisCacheTest(
    private val oneTimeCacheTestClient: OneTimeCacheTestClient,
) : ShouldSpec({
    should("respect the cache") {
        val result = oneTimeCacheTestClient.getString("THIS")
        // This should skip the counter-based response.
        val result2 = oneTimeCacheTestClient.getString("THIS")
        result.shouldBeInstanceOf<GeneralDTO.Success<String>>()
        result2.shouldBeInstanceOf<GeneralDTO.Success<String>>()
    }
}) {
    @Requires(property = "spec.name", value = "RedisCacheTest")
    @Singleton
    @CacheConfig
    open class OneTimeCacheTestClient {
        private val counter = AtomicInteger(0)

        @Cacheable(value = ["redis1"], parameters = ["string"])
        open fun getString(
            @QueryValue string: String,
        ): GeneralDTO<String> =
            if (counter.getAndIncrement() == 0) {
                GeneralDTO.success("$string 1")
            } else {
                GeneralDTO.error("Only fetch once")
            }
    }
}