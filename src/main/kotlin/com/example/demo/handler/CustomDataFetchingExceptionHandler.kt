package com.example.demo.handler

import com.example.demo.exception.DemoException
import com.netflix.graphql.dgs.client.GraphQLError
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture


@Component
class CustomDataFetchingExceptionHandler : DataFetcherExceptionHandler {
    override fun handleException(handlerParameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        return if (handlerParameters.exception is DemoException) {
            val extentions: MutableMap<String, Any> = HashMap()
            extentions["ERROR_CODE"] = (handlerParameters.exception as DemoException).errorCode
            val graphqlError: graphql.GraphQLError = TypedGraphQLError.newInternalErrorBuilder()
                    .message((handlerParameters.exception as DemoException).message)
                    .extensions(extentions)
                    .location(handlerParameters.sourceLocation)
                    .path(handlerParameters.path).build()
            val result = DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build()
            CompletableFuture.completedFuture(result)
        } else {
            super.handleException(handlerParameters)
        }
    }
}
