# Fauxtels

Fauxtels is a fictious hotel reservation application.

## Compiling and Running

To compile and run the command line application, execute the following:

    mvn compile exec:java

## Database

While running, the application is backed by an in memory database which can be accessed using the following credentials:

    url=http://localhost:8082
    jdbc=jdbc:h2:mem:fauxtel
    user=admin
    pass=admin
