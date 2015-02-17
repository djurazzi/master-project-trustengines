# master-project-trustengines
Implementations of different trust engines
==========================================

How to run the application?
---------------------------

Prerequisites:

  1. Java Development Kit 8 has to be installed (older versions work as well) -
      http://www.oracle.com/technetwork/java/javase/downloads/index.html
  2. Apache Maven 3.2.5 has to be installed (older versions work as well) - 
      http://maven.apache.org/download.cgi
  3. MySQL Community Server 5.6.22 (older versions work as well) - 
      http://dev.mysql.com/downloads/mysql/
  
Starting up the project:

  1. Create a user in MySQL server (default user used in hibernate.cfg.xml file is - username:"user" password:"password")
  2. Create a database (default database name used in hibernate.cfg.xml file is "mydb")
  3. Create necessary tables in the database using /src/main/resources/dbschema_script.sql file
  4. Download and unpack DiplProjekt2 on your computer
  5. If using Windows, run command prompt as an administrator
  6. First you need to fill up the database, this is done as follows:
    * Open /.../DiplProjekt2 directory
    * run the following command: "mvn compile exec:java -Dexec.mainClass=com.mastertheboss.DbFiller"
  7. Now that the database if filled with data, application can be run by typing the following command:
    * "mvn compile exec:java -Dexec.mainClass=com.duricic.App"
    
Files used for input in the application are the ratings_data.txt and the trust_data.txt files.
Output files are the calculated_ratings[1-4].txt files.


