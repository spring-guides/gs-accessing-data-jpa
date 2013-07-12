<#assign project_id="gs-accessing-data-jpa">

Getting Started: Accessing Data with JPA
============================================

What you'll build
-----------------

This guide walks you through the process of building an application that uses Spring Data JPA to store and retrieve data in a relational database.

What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>

## <@how_to_complete_this_guide jump_ahead='Define a simple entity'/>


<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>

### Create a Maven POM

    <@snippet path="pom.xml" prefix="complete"/>


<a name="initial"></a>
Define a simple entity
------------------------

In this example, you store Customer objects, annotated as a JPA entity.

    <@snippet path="src/main/java/hello/Customer.java" prefix="complete"/>

Here you have a `Customer` class with three attributes, the the `id`, the `firstName`, and the `lastName`. You also have two constructors. The default constructor only exists for the sake of JPA. You won't use it directly, so it is designated as `private`. The other constructor is the one you'll use to create instances of `Customer` to be saved to the database.

> Note: In this guide, the typical getters and setters have been left out for brevity.

This class is annotated with `@Entity`, indicating that it is a JPA entity. For lack of a `@Table` annotation, it is assumed that this entity will be mapped to a table named `Customer`.

The `Customer`'s `id` property is annotated with `@Id` so that JPA will recognize it as the object's ID. The `id` property is also annotated with `@GeneratedValue` to indicate that the ID should be generated automatically.

The other two properties, `firstName` and `lastName` are left unannotated. It is assumed that they'll be mapped to columns that share the same name as the properties themselves.

The convenient `toString()` method will print out the customer's properties.

Create simple queries
----------------------------
Spring Data JPA focuses on storing data in a relational database using JPA. It's most compelling feature is the ability to automatically create repository implementations, at runtime, from a repository interface.

To see how this works, create a repository interface that works with `Customer` entities:

    <@snippet path="src/main/java/hello/CustomerRepository.java" prefix="complete"/>
    
`CustomerRepository` extends the `JpaRepository` interface. The type of entity and ID that it works with (`Customer` and `Long`) are specified in the generic parameters on `JpaRepository`. By extending `JpaRepository`, `CustomerRepository` inherits several methods for working with `Customer` persistence, including methods for saving, deleting, and finding `Customer` entities.

Spring Data JPA also allows you to define other query methods by simply declaring their method signature. In the case of `CustomerRepository`, this is shown with a `findByLastName()` method.

In a typical Java application, you'd expect to write a class that implements `CustomerRepository`. But that's what makes Spring Data JPA so powerful: You don't have to write an implementation of the repository interface. Spring Data JPA will create an implementation on the fly when you run the application.

Let's wire this up and see what it looks like!

Create an application class
---------------------------
Here you create an Application class with all the components.

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>

In the configuration, you need to add the `@EnableJpaRepositories` annotation. This tells Spring Data JPA that it should seek out any interface that extends `org.springframework.data.repository.Repository` and to automatically generate an implementation. By extending `JpaRepository`, your `CustomerRepository` interface transitively extends `Repository`. Therefore, Spring Data JPA will find it and create an implementation for you.

Most of the content in `Application` just sets up several beans to support Spring Data JPA and the sample: 

 * The `dataSource()` method defines a `DataSource` bean, as an embedded database for the objects to be persisted to. 
 * The `entityManagerFactory()` method defines a `LocalContainerEntityManagerFactoryBean` that will ultimately be used to create an `EntityManagerFactory` through which JPA operations will be performed. Note that its `packagesToScan` property is set so that it will look for entities in the package named "hello". This makes it possible to work with JPA without defining a "persistence.xml" file.
 * The `jpaVendorAdapter()` method defines a Hibernate-based JPA vendor adaptor bean for use by the `EntityManagerFactory` bean.
 * The `transactionManager()` method defines a `JpaTransactionManager` bean for transactional persistence.

Finally, `Application` includes a `main()` method that puts the `CustomerRepository` through a few tests. First, it fetches the `CustomerRepository` from the Spring application context. Then it saves a handful of `Customer` objects, demonstrating the `save()` method and setting up some data to work with. Next, it calls `findAll()` to fetch all `Customer` objects from the database. Then it calls `findOne()` to fetch a single `Customer` by its ID. Finally, it calls `findByLastName()` to find all customers whose last name is "Bauer".

## <@build_the_application/>
    
Run the application
-----------------------
Run your service with `java -jar` at the command line:

    java -jar target/${project_id}-0.1.0.jar
    
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
Congratulations! You've written a simple application that uses Spring Data JPA to save some objects to a database and to fetch them; all without writing a concrete repository implementation.
