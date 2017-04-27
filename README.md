# h2o-kmeans
Sample usage of machine learning library (H2O). Testing different scenarios on the Hadoop cluster.

## Build
This is how you can build your app
```
mvn clean install
```

## Run
```
java -jar target/h2o-kmeans-1.0-SNAPSHOT-jar-with-dependencies.jar
hadoop jar h2o-kmeans-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## TODO
* Export model as POJO from H2O
* Import and use POJO model on Hadoop cluster 
* Test distributed computing
