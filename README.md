# graphql-spqr-blaze

http://localhost:8082/graphiql


```json
query all_cats {
  allCats(
  firstResult: 0,
  maxResults: 100,
  expression: "c.kittens.id=3 or c.kittens.id=4",
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
}
}
```
