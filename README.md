
Getting Started: Accessing Data with JPA
============================================

What you'll build
-----------------

This guide walks you through the process of building an application that uses Spring Data JPA to store and retrieve data in a relational database.

What you'll need
----------------

 - About 15 minutes
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Maven 3.0][mvn] or later

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[mvn]: http://maven.apache.org/download.cgi

How to complete this guide
--------------------------

Like all Spring's [Getting Started guides](/getting-started), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

To **start from scratch**, move on to [Set up the project](#scratch).

To **skip the basics**, do the following:

 - [Download][zip] and unzip the source repository for this guide, or clone it using [git](/understanding/git):
`git clone https://github.com/springframework-meta/gs-accessing-data-jpa.git`
 - cd into `gs-accessing-data-jpa/initial`
 - Jump ahead to [Define a simple entity](#initial).

**When you're finished**, you can check your results against the code in `gs-accessing-data-jpa/complete`.
[zip]: https://github.com/springframework-meta/gs-accessing-data-jpa/archive/master.zip


<a name="scratch"></a>
Set up the project
------------------

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Maven](https://maven.apache.org) and [Gradle](http://gradle.org) is included here. If you're not familiar with either, refer to [Building Java Projects with Maven](../gs-maven/README.md) or [Building Java Projects with Gradle](../gs-gradle/README.md).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

### Create a Maven POM

`pom.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.springframework.</groupId>
    <artifactId>gs-accessing-data-jpa</artifactId>
    <version>0.1.0</version>

    <parent>
        <groupId>org.springframework.bootstrap</groupId>
        <artifactId>spring-bootstrap-starters</artifactId>
        <version>0.5.0.BUILD-SNAPSHOT</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.bootstrap</groupId>
            <artifactId>spring-bootstrap-web-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <!-- use UTF-8 for everything -->
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <start-class>hello.Application</start-class>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>http://repo.springsource.org/snapshot</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>http://repo.springsource.org/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>org.jboss.repository.releases</id>
            <name>JBoss Maven Release Repository</name>
            <url>https://repository.jboss.org/nexus/content/repositories/releases</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <name>Spring Snapshots</name>
            <url>http://repo.springsource.org/snapshot</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </pluginRepository>
        <pluginRepository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>http://repo.springsource.org/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>

</project>
```


<a name="initial"></a>
Define a simple entity
------------------------

In this example, you store Customer objects, annotated as a JPA entity.

`src/main/java/hello/Customer.java`
```java
package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;

    private Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}

```

Here you have a `Customer` class with three attributes, the `id`, the `firstName`, and the `lastName`. You also have two constructors. The default constructor only exists for the sake of JPA. You won't use it directly, so it is designated as `private`. The other constructor is the one you'll use to create instances of `Customer` to be saved to the database.

> Note: In this guide, the typical getters and setters have been left out for brevity.

The `Customer` class is annotated with `@Entity`, indicating that it is a JPA entity. For lack of a `@Table` annotation, it is assumed that this entity will be mapped to a table named `Customer`.

The `Customer`'s `id` property is annotated with `@Id` so that JPA will recognize it as the object's ID. The `id` property is also annotated with `@GeneratedValue` to indicate that the ID should be generated automatically.

The other two properties, `firstName` and `lastName` are left unannotated. It is assumed that they'll be mapped to columns that share the same name as the properties themselves.

The convenient `toString()` method will print out the customer's properties.

Create simple queries
----------------------------
Spring Data JPA focuses on using JPA to store data in a relational database. Its most compelling feature is the ability to create repository implementations automatically, at runtime, from a repository interface.

To see how this works, create a repository interface that works with `Customer` entities:

`src/main/java/hello/CustomerRepository.java`
```java
package hello;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    List<Customer> findByLastName(String lastName);
    
}
```
    
`CustomerRepository` extends the `JpaRepository` interface. The type of entity and ID that it works with,`Customer` and `Long`, are specified in the generic parameters on `JpaRepository`. By extending `JpaRepository`, `CustomerRepository` inherits several methods for working with `Customer` persistence, including methods for saving, deleting, and finding `Customer` entities.

Spring Data JPA also allows you to define other query methods by simply declaring their method signature. In the case of `CustomerRepository`, this is shown with a `findByLastName()` method.

In a typical Java application, you'd expect to write a class that implements `CustomerRepository`. But that's what makes Spring Data JPA so powerful: You don't have to write an implementation of the repository interface. Spring Data JPA creates an implementation on the fly when you run the application.

Let's wire this up and see what it looks like!

Create an Application class
---------------------------
Here you create an Application class with all the components.

`src/main/java/hello/Application.java`
```java
package hello;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories
public class Application {
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("hello");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        CustomerRepository repository = context.getBean(CustomerRepository.class);
        
        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
        
        // fetch all customers
        List<Customer> customers = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();
        
        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();
        
        // fetch customers by last name
        List<Customer> bauers = repository.findByLastName("Bauer");
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : bauers) {
            System.out.println(bauer);
        }
        
        context.close();
    }

}
```

In the configuration, you need to add the `@EnableJpaRepositories` annotation. This annotation tells Spring Data JPA that it should seek out any interface that extends `org.springframework.data.repository.Repository` and automatically generate an implementation. By extending `JpaRepository`, your `CustomerRepository` interface transitively extends `Repository`. Therefore, Spring Data JPA will find it and create an implementation for you.

Most of the content in `Application` sets up several beans to support Spring Data JPA and the sample: 

 * The `dataSource()` method defines a `DataSource` bean, as an embedded database to which the objects are persisted. 
 * The `entityManagerFactory()` method defines a `LocalContainerEntityManagerFactoryBean` that is ultimately used to create `LocalContainerEntityManagerFactory` a bean that implements the `EntityManagerFactory` interface. It is the bean through which JPA operations will be performed. Note that this factory bean's `packagesToScan` property is set to look for entities in the package named "hello". This makes it possible to work with JPA without defining a "persistence.xml" file.
 * The `jpaVendorAdapter()` method defines a Hibernate-based JPA vendor adapter bean for use by the `EntityManagerFactory` bean.
 * The `transactionManager()` method defines a `JpaTransactionManager` bean for transactional persistence.

Finally, `Application` includes a `main()` method that puts the `CustomerRepository` through a few tests. First, it fetches the `CustomerRepository` from the Spring application context. Then it saves a handful of `Customer` objects, demonstrating the `save()` method and setting up some data to work with. Next, it calls `findAll()` to fetch all `Customer` objects from the database. Then it calls `findOne()` to fetch a single `Customer` by its ID. Finally, it calls `findByLastName()` to find all customers whose last name is "Bauer".

Build an executable JAR
-----------------------

Now that your `Application` class is ready, you simply instruct the build system to create a single, executable jar containing everything. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

Add the following configuration to your existing Maven POM:

`pom.xml`
```xml
    <properties>
        <start-class>hello.Application</start-class>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.zero</groupId>
                <artifactId>spring-package-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

The `start-class` property tells Maven to create a `META-INF/MANIFEST.MF` file with a `Main-Class: hello.Application` entry. This entry enables you to run the jar with `java -jar`.

The [Spring Package maven plugin][spring-package-maven-plugin] collects all the jars on the classpath and builds a single "über-jar", which makes it more convenient to execute and transport your service.

Now run the following to produce a single executable JAR file containing all necessary dependency classes and resources:

    mvn package

[spring-package-maven-plugin]: https://github.com/SpringSource/spring-zero/tree/master/spring-package-maven-plugin

> **Note:** The procedure above will create a runnable JAR. You can also opt to [build a classic WAR file](/guides/gs/convert-jar-to-war/content) instead.
    
Run the application
-------------------
Run your application with `java -jar` at the command line:

    java -jar target/gs-accessing-data-jpa-0.1.0.jar


    
You should see something like this:
```
Customers found with findAll():
-------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=2, firstName='Chloe', lastName='O'Brian']
Customer[id=3, firstName='Kim', lastName='Bauer']
Customer[id=4, firstName='David', lastName='Palmer']
Customer[id=5, firstName='Michelle', lastName='Dessler']

Customer found with findOne(1L):
--------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']

Customer found with findByLastName('Bauer'):
--------------------------------------------
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=3, firstName='Kim', lastName='Bauer']
```

Summary
-------
Congratulations! You've written a simple application that uses Spring Data JPA to save objects to a database and to fetch them -- all without writing a concrete repository implementation.
