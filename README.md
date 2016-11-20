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

2. Make sure to pass the path to the DB as JAVA_OPT 
(in IDEA, add `-DDB_PATH="db path"` to VM options).
Example: `-DDB_PATH="localhost:5432/your_db`

3. Run Tomcat server