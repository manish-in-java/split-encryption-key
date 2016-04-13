[![Build Status](https://drone.io/github.com/manish-in-java/split-encryption-key/status.png)](https://drone.io/github.com/manish-in-java/split-encryption-key/latest)
[![Custom License](http://b.repl.ca/v1/License-CUSTOM-red.png)](#LICENSE)
[![Spring WebMVC Application](http://b.repl.ca/v1/spring-mvc-blue.png)](#SWMVC)

# Background
It is frequently required to encrypt sensitive data before storing it in a
persistent store such as a relational database. It is also desirable for each
unique record to have its own encryption key. This ensures that if the key for
one record is leaked somehow, it will affect only that record and none others.
As an example, consider that an application stores a person's social benefits
information, such as US Social Security Numbers. Such information being
confidential information of a person, would have to be treated as sensitive for
the application. Consequently, the application would like to encrypt such
information when persisting it to a database. The application would like to
assign separate encryption keys to each person so that the sensitive
information for each person is encrypted using their own unique key. A key
getting revealed would then affect only that person to whom it was assigned.
All other persons would remain unaffected.

In such scenarios, the key cannot be stored alongside the information being
protected. In the example above, if each person gets a separate record in
the database, the social benefits information for each person is being stored
in the record allocated to that person. The encryption key for each person
cannot be stored in the same record. Doing so would allow someone with access
to the database to extract both the sensitive information stored in encrypted
form and the keys used to perform the encryption.

A desirable mechanism would therefore be to store the keys away from the
encrypted data, possibly in a safe storage of its own so that it becomes at
least difficult, if not impossible, for a single person to gain access to the
encryption source code, encrypted data and encryption keys.

# Overview
This application presents an option for allocating separate encryption keys to
each unique record. It stores person information in a database table named
`PERSON`. The table contains four columns - `FIRST_NAME`, `LAST_NAME`,
`SOCIAL_BENEFITS_NUMBER` and `SECRET` that store a person's first name, last
name, a unique social benefits number and a secret text that is used to derive
the encryption key for that person. The column `SOCIAL_BENEFITS_NUMBER`
contains encrypted values so that someone with direct access to the database
cannot glean the actual social benefits numbers.

The encryption key for each person is derived by combining a fixed passphrase
with the person's secret. The passphrase can be stored outside the application,
making it difficult for a application developer to get the encryption keys.
For example, the passphrase can be read from a PGP vault at runtime or a
hardware device. This application uses a hard-coded passphrase as the intent
is mainly to test dynamic generation of encryption keys instead of securing
the passphrase.

The encryption key for a person is generated dynamically only when sensitive
personal information needs to be retrieved. The key remains inaccessible
otherwise.

# Pre-requisites
* JDK 1.8 or later
* Apache Maven
* Internet connection

# Running the application
After downloading or checking out the application, run it as
`mvn clean package tomcat7:run`. This will start an embedded Apache Tomcat
instance.

Once Tomcat startup is complete, open a web browser and navigate to
[http://localhost:8080](http://localhost:8080).

# Testing the application
1. When the application loads in the browser, add information using the form provided on the application home page. The information will be added to an in-memory H2 database and will be displayed on the screen as well. Compare the displayed information with that provided to make sure that the typed and displayed values are the same.
1. Open an administration console for the in-memory H2 database by opening a web browser and navigating to [http://localhost:8080/database](http://localhost:8080/database).
1. Change/set the JDBC URL is to `jdbc:h2:mem:split-encryption-key`.
1. Click on the button titled `Connect` to open the H2 database.
1. Load all records from the `PERSON` table and ensure that the social benefits numbers are not shown in plain.

# License
This sample application and its associated source code in its entirety is being made
available under the following licensing terms.

    Copyright (C) 2015

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in the
    Software without restriction, including without limitation the rights to use, copy,
    modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
    and to permit persons to whom the Software is furnished to do so, subject to the
    following conditions:

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
    PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
    CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
    OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
