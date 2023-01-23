
# Hibernate Project Demonstration

####  Develop CRUD Operations


## Project Structure
```
.
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org
    │   │       └── demo
    │   │           └── hibernate
    │   │               ├── App.java
    │   │               ├── controller
    │   │               │   ├── CreateUser.java
    │   │               │   ├── CreateUserTable.java
    │   │               │   ├── DeletingUser.java
    │   │               │   ├── FindUserBy.java
    │   │               │   ├── FindingUser.java
    │   │               │   └── UpdatingUser.java
    │   │               ├── model
    │   │               │   └── User.java
    │   │               └── service
    │   └── resources
    │       └── hibernate.cfg.xml
    └── test
        └── java
            └── org
                └── demo
                    └── hibernate
                        └── AppTest.java




```
## POM.XML
Add Jar dependencies,and configuration in pom.xml file\
Next, we need to add a couple of jar dependencies for Hibernate, JPA and MySQL Connector Java in the pom.xml file of our Maven’s Project. \
Open the pom.xml file and insert the following XML code under <dependencies> </dependencies> tag,  just  before the </project> tag\
Here we added two dependencies for the project: hibernate-core and MySQL-connection. Maven automatically downloads the required JAR files, which are shown under the Maven Dependencies node in the project

```bash
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.demo.hibernate</groupId>
  <artifactId>HibernateDemo2</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>HibernateDemo2</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>

  <dependencies>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.31</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>5.5.7.Final</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-annotations -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-annotations</artifactId>
      <version>3.5.6-Final</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

## Create the Persistence Class (Model Class or Pojo).
- Create a package model 
- Create entity class named “User.java” and "Book.java" under the above package.
- Then we will use annotations to map this table to the corresponding table in the database.
This is a POJO (Plain Old Java Object) class with some class variables, its getter, setter methods, and constructors.\
Use some annotations provided by JPA to map this model class to the user table in the database.


```bash
package org.demo.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Entity annotation has primary key id
@Entity
//change table name, column name as follows
//@Table(name = "library_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    //Default constructor
    public User() {
    }

    public User(int id, String userId, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    //Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


```
```bash
package org.demo.hibernate.model;

import javax.persistence.*;

//Entity annotation has primary key id
@Entity
@Table(name = "books")
@NamedQuery(name = "getAllBooks", query = "from Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bookName;
    private String ISBN;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}


```


    

## Create the Configuration File

- For Eclipse IDE: To create the configuration file, To do so, right click on src/main/java →   New → Other - search files from search panel → click on File → specify the file name “hibernate.cfg.xml” → Finish. Write the following code in it.
- For intellij IDE: Create a configuration file named hibernate.cfg.xml under the resources folder, and write the following code in it.\
hibernate.cfg.xml

```bash
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Drop and re-create the database on startup -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Database connection settings -->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/library</property>
        <property name="connection.username">{username}</property>
        <property name="connection.password">{password}</property>

        <!-- MySQL DB dialect -->
        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>
        <!-- print all executed SQL on console -->
        <property name="hibernate.show_sql" >true</property>
        <property name="hibernate.format_sql" >true</property>

        <!--   Mapping entity file -->
        <mapping class="org.demo.hibernate.model.User"/>
        <mapping class="org.demo.hibernate.model.Book"/>
     </session-factory>
 </hibernate-configuration>
```


## Develop CRUD Operations

Establish the CRUD with service and Interface

## Create User

Add the following code to the UserService  interface
```bash
package org.demo.hibernate.service;

import org.demo.hibernate.model.User;

public interface UserService {
    void createUser(User user);
}
```
Add the following code to the UserServiceImpl class
```bash
package org.demo.hibernate.service;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.UUID;

public class UserServiceImpl implements UserService{
    @Override
    public void createUser(User user1) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(user1);
        t.commit();
        factory.close();
        session.close();
        System.out.println("successfully created user "+user1.getFirstName()+" "+user1.getLastName());
    }
}

```
Add the following code to the App.java
```bash
package org.demo.hibernate;

import org.demo.hibernate.model.User;
import org.demo.hibernate.service.UserService;
import org.demo.hibernate.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.UUID;


public class App 
{
    public static void main( String[] args )
    {
        UserService userService = new UserServiceImpl();
        User user = new User(1, UUID.randomUUID().toString(), "May", "Falcon", "user@test.com", "209-657-0000") ;
        userService.createUser(user);
    }
}

```
Run App.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49921:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2Impl/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.App
Hibernate: 
    insert 
    into
        User
        (email, firstName, lastName, phoneNumber, userId) 
    values
        (?, ?, ?, ?, ?)
successfully created user May Falcon
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 7 | user@test.com  |May  |Falcon  |209-657-0000  |2b3d149a-6932-44ef-8a14-a930aa6e594c  |


## Adding User Records in the Database

Add the following code to the UserService  interface
```bash
void updateUser(User user, String phoneNumber);
```
Add the following code to the UserServiceImpl class
```bash
@Override
    public void updateUser(User user, int id) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        user.setPhoneNumber(phoneNumber);
        session.merge(user);
        session.getTransaction().commit();
        factory.close();
        session.close();
        System.out.println("successfully updated user "+user.getFirstName()+" "+user.getLastName()+ " phone number to "+user.getPhoneNumber());
    }
```
Add the following code to the App.java
```bash
        UserService userService = new UserServiceImpl();
        User user = new User(1, UUID.randomUUID().toString(), "May", "Falcon", "user@test.com", "209-657-0000") ;
        userService.updateUser(user,"924-875-1041");
```
Run App.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49933:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2Impl/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.App
Hibernate: 
    select
        user0_.id as id1_0_0_,
        user0_.email as email2_0_0_,
        user0_.firstName as firstnam3_0_0_,
        user0_.lastName as lastname4_0_0_,
        user0_.phoneNumber as phonenum5_0_0_,
        user0_.userId as userid6_0_0_ 
    from
        User user0_ 
    where
        user0_.id=?
Hibernate: 
    insert 
    into
        User
        (email, firstName, lastName, phoneNumber, userId) 
    values
        (?, ?, ?, ?, ?)
successfully updated user May Falcon phone number to 924-875-1041
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 7 | user@test.com  |May  |Falcon  |209-657-0000  |2b3d149a-6932-44ef-8a14-a930aa6e594c  |
| 8 | user@test.com  |May  |Falcon  |924-875-1041 |5abfe7d2-34a4-4265-8d8d-717e4800f402 |


## READ all the Users

Add the following code to the UserService  interface
```bash
List<User> getAllUsers();
```
Add the following code to the UserServiceImpl class
```bash
@Override
    public List<User> getAllUsers() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM User";
        TypedQuery query = session.createQuery(hql);
        List<User> results = query.getResultList();
        factory.close();
        session.close();
        return results;
    }
```
Add the following code to the App.java
```bash
        UserService userService = new UserServiceImpl();
        List<User> list = userService.getAllUsers();
        for(User u : list){
            System.out.println(u.getId());
        }
```
Run App.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49933:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2Impl/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.App
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.email as email2_0_,
        user0_.firstName as firstnam3_0_,
        user0_.lastName as lastname4_0_,
        user0_.phoneNumber as phonenum5_0_,
        user0_.userId as userid6_0_ 
    from
        User user0_
7
8
6
9
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT id FROM USER;
```
| id |
| :-------- |
| 7 | 
| 8 | 
| 6 | 
| 9 | 

## Find User By Email

Add the following code to the UserService  interface
```bash
User findUserByEmail(String email);
```
Add the following code to the UserServiceImpl class
```bash
@Override
    public User findUserByEmail(String email) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "FROM User u WHERE u.email =:email";
        TypedQuery query = session.createQuery(hql, User.class);
        query.setParameter("email",email);
        User user = (User) query.getSingleResult();
        factory.close();
        session.close();
        return user;
    }
```
Add the following code to the App.java
```bash
        UserService userService = new UserServiceImpl();
        User userEmail = userService.findUserByEmail("flower@test.com");
        System.out.println("Found user "+userEmail.getFirstName()+" "+userEmail.getLastName()+ " with the email "+userEmail.getEmail());
```
Run App.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49933:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2Impl/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.App
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.email as email2_0_,
        user0_.firstName as firstnam3_0_,
        user0_.lastName as lastname4_0_,
        user0_.phoneNumber as phonenum5_0_,
        user0_.userId as userid6_0_ 
    from
        User user0_ 
    where
        user0_.email=?
Found user June Valley with the email flower@test.com
Process finished with exit code 0
```

## Performing DELETE operation

Add the following code to the UserService  interface
```bash
void delete(int id);
```
Add the following code to the UserServiceImpl class
```bash
@Override
    public void delete(int id) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User u = new User();
        u.setId(id);
        session.delete(u);
        t.commit();
        factory.close();
        session.close();
    }
```
Add the following code to the App.java
```bash
        UserService userService = new UserServiceImpl();
        userService.delete(7);
```
Run App.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=49933:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2Impl/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.App
Hibernate: 
    select
        user0_.id as id1_0_0_,
        user0_.email as email2_0_0_,
        user0_.firstName as firstnam3_0_0_,
        user0_.lastName as lastname4_0_0_,
        user0_.phoneNumber as phonenum5_0_0_,
        user0_.userId as userid6_0_0_ 
    from
        User user0_ 
    where
        user0_.id=?
Hibernate: 
    insert 
    into
        User
        (email, firstName, lastName, phoneNumber, userId) 
    values
        (?, ?, ?, ?, ?)
successfully updated user May Falcon phone number to 924-875-1041
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 8 | user@test.com  |May  |Falcon  | 209-657-0000|b83e4bf1-3235-4e5c-af63-14d5b56f38d7|
| 6 | user@test.com  |John  |Smith  |924-875-1041  |085da9b4-53e3-40af-957e-e38f5162a2f1|
| 10 | flower@test.com  |June  |Valley  |204-913-0000 |aea418ea-66ea-4bfc-9bb3-c588327a153b|


## Author

- [@abijay](https://github.com/abi-jay)

