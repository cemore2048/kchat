# KChat - name is WIP
## Requirements
1. jdk installed and setup in your editor 
2. gradle installed
3. If using intellj can use the following to open project
![alt text](https://i.stack.imgur.com/l68gI.png)


## Setup 

1. In the resources folder, add a `hikari.properties` file
- add this configuration 
    ```
    dataSourceClassName=org.postgresql.ds.PGSimpleDataSource
    dataSource.user=postgres
    dataSource.password=postgres
    dataSource.databaseName=postgres
    dataSource.portNumber=5432
    dataSource.serverName=localhost
    ```


That's basically it right now....

## running kchat in docker
### build first
`docker-compose build`
### deploy
`docker-compose up`

