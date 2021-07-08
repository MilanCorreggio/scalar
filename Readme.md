Sclardb travel

Delete and add objects in the database using Emoney model from getting started.

Start the project running
`./gradlew installDist`

Start docker container using
`docker compose up -d`

Install the table using
`java -jar scalar-schema-standalone-3.0.0.jar --jdbc -j jdbc:mysql://localhost:3306/ -u root -p password -f travel-transaction.json`

first, you need to move in app directory: `cd app`

Example to add and delete flights

 To add: `../gradlew run --args="-mode add -price 1000 -to Osaka -from Tokyo"`

 To delete: `../gradlew run --args="-mode delete -id id"`

 To read: `../gradlew run --args="-mode read -id id"`

 To update: `../gradlew run --args="-mode updatePrice -id id -price 10000"`
