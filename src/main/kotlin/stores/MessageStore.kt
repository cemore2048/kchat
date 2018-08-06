package stores

import io.ktor.http.Parameters
import models.Message
import models.Message.payload


object MessageStore: BaseStore<Message>(Message) {

    suspend fun create(params: Parameters): String? {
        return create {
            it.first[payload] = params["payload"]!!
        }
    }

}