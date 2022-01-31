# graphql-spqr-blaze

http://localhost:8082/graphiql
or
http://localhost:8082/gui -> URL: http://localhost:8082/graphql


```graphql
query all_cats {
    allCats(
        firstResult: 0,
        maxResults: 100,
        expression: "c.kittens.name='Alex-1' or c.aliases.alias='Bernie'",
        sortOptions: [
            {sortBy: "id", sortOrder: DESC},
            {sortBy: "name", sortOrder: DESC}
        ]
    ) {
        id
        name
        kittens {
            id
            name
        }
        aliases{
            id
            alias
        }
    }
}
```
