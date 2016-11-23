[freemarker]: http://freemarker.org/freemarkerdownload.html
[org.json]: https://mvnrepository.com/artifact/org.json/json/20160810
[jdbc]: https://jdbc.postgresql.org/download.html

#IT Project 

Used computers marketplace

- [x] Authorization
- [x] Profile
- [x] Items
- [x] Search
- [x] Reviews
- [x] Messages

##Usage
1. Install required libs: 
    - [Freemarker][freemarker] v. >= 2.3.25
    - [org.json]
    - [Postgres JDBC][jdbc] v. >= 9.4

2. Make sure to pass the path to the DB as JAVA_OPT `DB_PATH`, and 
also you need `DB_USER` and `DB_PASSWORD`

    In IDEA, just add this to VM options:
     
     `-DDB_PATH="db path" -DDB_USER=user -DDB_PASSWORD=pass`
     
     Example: 
     
     `-DDB_PATH="localhost:5432/your_db" -DDB_USER=postgres -DDB_PASSWORD=postgres`


3. Run Tomcat server