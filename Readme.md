#Register and delete traveling flights

Start the project running
`./gradlew installDist`

Install scalar-schema-standalone-3.0.0.jar [here](https://github.com/scalar-labs/scalardb/releases)

Start docker container using 
`docker compose up -d`

Install the table using 
`java -jar scalar-schema-standalone-3.0.0.jar --jdbc -j jdbc:mysql://localhost:3306/ -u root -p password -f travel-transaction.json`


first, you need to move in app directory:
`cd app`


###Example to add and delete flights


To add:

`../gradlew run --args="-mode add -price 1000 -to Osaka -from Tokyo"`

to delete:

`../gradlew run --args="-mode delete -id id"`
