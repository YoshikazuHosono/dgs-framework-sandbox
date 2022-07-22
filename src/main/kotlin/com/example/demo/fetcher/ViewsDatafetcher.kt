package com.example.demo.fetcher

import com.example.demo.exception.DemoException
import com.example.demo.gen.graphql.DgsConstants
import com.example.demo.gen.graphql.types.Show
import com.example.demo.gen.graphql.types.View
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import java.lang.RuntimeException


@DgsComponent
class ViewsDatafetcher {

    private val shows = listOf(
            View("Stranger Things", 2016),
            View("Ozark", 2017),
            View("The Crown", 2016),
            View("Dead to Me", 2019),
            View("Orange is the New Black", 2013)
    )

    @DgsQuery(field = DgsConstants.QUERY.Views)
    fun showsQuery(@InputArgument titleFilter: String?): List<View> {
        if (titleFilter != null && titleFilter == "error") throw RuntimeException("you need err ???")
        if (titleFilter != null && titleFilter == "error2") throw DemoException("you need custom err ???", "A0001")

        return if (titleFilter == null) shows
        else shows.filter { it.title?.contains(titleFilter) ?: false }
    }

}

