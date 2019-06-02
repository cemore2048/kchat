package routing

import io.ktor.http.Parameters

object RoutingUtil {
    fun getMissingFields(requiredParams: List<String>, params: Parameters) =
            requiredParams.filter {
                params[it].isNullOrBlank()
            }
}