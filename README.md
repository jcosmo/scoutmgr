Myproject - My project description
==================================

What is Myproject
--------------

Myproject is a template project that demonstrates a typical project setup.

How-to Build
------------

Myproject uses [Apache Buildr](http://buildr.apache.org) to build the project which is a ruby based build tool. The easiest way to build the project is to use [rbenv](https://github.com/sstephenson/rbenv) to manage the ruby version and [bundler](http://gembundler.com/) to manage the gem dependencies for buildr.

Under OSX with [Homebrew](http://mxcl.github.com/homebrew/) installed you can install ruby via;

    $ brew update
    $ brew install rbenv
    $ brew install ruby-build
    $ ruby-build install 2.1.3

Note that if you are OSX and the 2.1.3 version is not listed when you do:

    $ rbenv install --list

you may need to do a:

    $ brew upgrade ruby-build

To install Buildr and the other gem dependencies you then need to do:

    $ cd ../path/to/myproject
    $ bundle install
    $ rbenv rehash

Finally to build you run the following commands

    $ bundle exec buildr clean package

How-to Configure
----------------

Before the application can be developed and run it is important to configure the local environment. The easiest way is to copy the template files and customize them for local development:

    $ cd ../path/to/myproject
    $ cp config/local.example.sh config/local.sh
    $ vi config/local.sh
    $ cp config/database.example.yml config/database.yml
    $ vi config/database.yml

How-to Generate Source
----------------------

The application uses domgen to generate source code from the `architecture.rb` DSL. To regenerate the source code you can use the buildr command:

    $ bundle exec buildr domgen:all

How-to Run
----------

Myproject uses SQL Server as the back end data store and runs in the Payara/GlassFish application server. At this stage it assumed that you have installed the correct version of Payara by hand and the SQL Server database server is already setup.

First you need to setup the database structure. This is done using the buildr command:

    $ bundle exec buildr dbt:create

After creating the database structure then you need to start up the Payara/GlassFish server and configure it via;

    $ alias asadmin=$PAYARA_HOME/glassfish/bin/asadmin
    $ source config/setup.sh

You can manually deploy the already built application via a command like the following:

    $ asadmin deploy --name myproject --contextroot myproject --force=true target/myproject-server/myproject-*.war

However this approach is not typically used in development, instead deployment is done through the IDE. See below for how to run the project from within the IDE.

After the application is deployed you can visit the local website at [http://127.0.0.1:8080/myproject](http://127.0.0.1:8080/myproject)

IDE
---

Our IDE of choice is IntelliJ IDEA and is the only supported IDE from which to build, test and debug the application.

You can generate the IDE configuration files via the buildr command:

    $ bundle exec buildr artifacts:sources idea:clean idea

This should configure the project ready for development. It may not be necessary to run the `idea:clean` task unless the project has changed significantly since the last rebuild of the project files.

It should be noted that to run the tests from within the IDE you need to have created the test database via:

    $ bundle exec buildr dbt:create DB_ENV=test

If you are running the integration tests it is also necessary to have built the server package from the command line. This can be done via:

    $ bundle exec buildr myproject:server:package TEST=no
