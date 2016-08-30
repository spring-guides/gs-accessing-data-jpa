---
tags: [spring-data, jpa]
projects: [spring-data-jpa]
---
:toc:
:icons: font
:source-highlighter: prettify
:project_id: gs-accessing-data-jpa
This guide walks you through the process of building an application that uses Spring Data JPA to store and retrieve data in a relational database.

== What you'll build

You'll build an application that stores `Customer` link:/understanding/POJO[POJOs] in a memory-based database.

== What you'll need

:java_version: 1.8
include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/prereq_editor_jdk_buildtools.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/how_to_complete_this_guide.adoc[]


include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-gradle.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-maven.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/hide-show-sts.adoc[]


[[initial]]
== Define a simple entity

In this example, you store Customer objects, annotated as a JPA entity.

`src/main/java/hello/Customer.java`
[source,java,indent=0]
----
include::complete/src/main/java/hello/Customer.java[tag=sample]
}
----

Here you have a `Customer` class with three attributes, the `id`, the `firstName`, and the `lastName`. You also have two constructors. The default constructor only exists for the sake of JPA. You won't use it directly, so it is designated as `protected`. The other constructor is the one you'll use to create instances of `Customer` to be saved to the database.

NOTE: In this guide, the typical getters and setters have been left out for brevity.

The `Customer` class is annotated with `@Entity`, indicating that it is a JPA entity. For lack of a `@Table` annotation, it is assumed that this entity will be mapped to a table named `Customer`.

The `Customer`’s `id` property is annotated with `@Id` so that JPA will recognize it as the object's ID. The `id` property is also annotated with `@GeneratedValue` to indicate that the ID should be generated automatically.

The other two properties, `firstName` and `lastName` are left unannotated. It is assumed that they'll be mapped to columns that share the same name as the properties themselves.

The convenient `toString()` method will print out the customer's properties.

== Create simple queries
Spring Data JPA focuses on using JPA to store data in a relational database. Its most compelling feature is the ability to create repository implementations automatically, at runtime, from a repository interface.

To see how this works, create a repository interface that works with `Customer` entities:

`src/main/java/hello/CustomerRepository.java`
[source,java]
----
include::complete/src/main/java/hello/CustomerRepository.java[]
----
    
`CustomerRepository` extends the `CrudRepository` interface. The type of entity and ID that it works with,`Customer` and `Long`, are specified in the generic parameters on `CrudRepository`. By extending `CrudRepository`, `CustomerRepository` inherits several methods for working with `Customer` persistence, including methods for saving, deleting, and finding `Customer` entities.

Spring Data JPA also allows you to define other query methods by simply declaring their method signature. In the case of `CustomerRepository`, this is shown with a `findByLastName()` method.

In a typical Java application, you'd expect to write a class that implements `CustomerRepository`. But that's what makes Spring Data JPA so powerful: You don't have to write an implementation of the repository interface. Spring Data JPA creates an implementation on the fly when you run the application.

Let's wire this up and see what it looks like!

== Create an Application class
Here you create an Application class with all the components.

`src/main/java/hello/Application.java`
[source,java]
----
include::complete/src/main/java/hello/Application.java[]
----

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/spring-boot-application.adoc[]

`Application` includes a `main()` method that puts the `CustomerRepository` through a few tests. First, it fetches the `CustomerRepository` from the Spring application context. Then it saves a handful of `Customer` objects, demonstrating the `save()` method and setting up some data to work with. Next, it calls `findAll()` to fetch all `Customer` objects from the database. Then it calls `findOne()` to fetch a single `Customer` by its ID. Finally, it calls `findByLastName()` to find all customers whose last name is "Bauer".

NOTE: By default, Spring Boot will enable JPA repository support and look in the package (and its subpackages) where `@SpringBootApplication` is located. If your configuration has JPA repository interface definitions located in a package not visible, you can point out alternate packages using `@EnableJpaRepositories` and its type-safe `basePackageClasses=MyRepository.class` parameter. 

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_an_executable_jar_mainhead.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_an_executable_jar_with_both.adoc[]
    
    
You should see something like this:
....
== Customers found with findAll():
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=2, firstName='Chloe', lastName='O'Brian']
Customer[id=3, firstName='Kim', lastName='Bauer']
Customer[id=4, firstName='David', lastName='Palmer']
Customer[id=5, firstName='Michelle', lastName='Dessler']

== Customer found with findOne(1L):
Customer[id=1, firstName='Jack', lastName='Bauer']

== Customer found with findByLastName('Bauer'):
Customer[id=1, firstName='Jack', lastName='Bauer']
Customer[id=3, firstName='Kim', lastName='Bauer']
....

== Summary
Congratulations! You've written a simple application that uses Spring Data JPA to save objects to a database and to fetch them -- all without writing a concrete repository implementation.

NOTE: If you're interesting in exposing JPA repositories with a hypermedia-based RESTful front end with little effort, you might want to read link:/guides/gs/accessing-data-rest[Accessing JPA Data with REST].

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc[]

