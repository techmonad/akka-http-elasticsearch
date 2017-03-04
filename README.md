# activator-akka-http-elasticsearch
  This is an activator project for showing case for querying and sending data to elasticsearch using Akka-http and how to write unit tests.


### Getting Started:
 Clone and run the unit test:
  ```
  $ git clone git@github.com:techmonad/activator-akka-http-elasticsearch.git
  $ cd activator-akka-http-elasticsearch
  $ bin/activator test
  ```

####Elasticsearch Setup
  i) [Download](https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/zip/elasticsearch/2.4.4/elasticsearch-2.4.4.zip) the Elasticsearch 2.4.4 or latest version  and unzip it.

  ii) Run the following command.

        $ bin/elasticsearch
 
### Run http server:
 ```
   $ bin/activator run

 ```

### Test the http rest point using curl:


1) Add the catalogue record 

 request:
 ```
   $  curl -XPOST 'localhost:9000/catalogue/add'  -d '{"id":1,"type":"book","author":"Martin Odersky","title":"Programming in Scala"}'
   ```
   
 response:
 ```
  Record added to catalogue successfully
 ```


2) Search by query

  request:
    
  ```
  $ curl -XPOST 'localhost:9000/catalogue/search -d '{"author":"martin"}'
  
  ```
  response:
 ```     
  [{"id":2,"type":"book","author":"Martin Odersky","title":"Programming in Scala"}]
 ```
