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

```graphql
mutation update_cat {
    updateCat(data: { id:"1" name: "Lux", description: "De" }) {
        id
        name
        description
    }
}
```

```graphql
mutation create_cat {
    createCat(data: { id:"1" name: "Lux", description: "De" }) {
        id
        name
        description
    }
}
```