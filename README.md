MIB
===

Microservice Infrastructure Blueprint - Get started with your microservice architecture based web-project within minutes

Mindset
===
Why even doing infrastructure stuff yourself in times of spring-io? Because with this infrastructure we basically can have a language agnostic blueprint (although we are using java at the moment and thus prepared the blueprint for exactly that).

We also want full controll of what libraries we include, because we care about the memory footprint in microservice development. As a startup, building a real product, we want to be able to deploy many microservices on few server instances in the beginning for cost reduction.

At the same time we don't want to spend time configuring every single microservice again. So we build MIB with a ready-for-development approach. That means our goal by using MIB is to get to business code development within 5 minutes after an initial clone.

Usage
===
The blueprint directory acts as the ... blueprint ;) for all of your services.

By executing the following commands, you create a new microservice project from the blueprint. 

```
gradle createMS -Pmsname="name of your microservice"
gradle idea
```

What is MIB meant to be?
===
We want to provide a way of creating a microservice with just 1 command line call so that developers get a running microservice as fast as possible. So when changing the blueprint, always keep in mind that microservices constructed from that blueprint should still run out of the box.

We ultimately plan to provide infrastructure and utils covering the following features:
- A default embedded jetty configuration
- A default kafka configuration starting up a consumer upon service startup
- A way of being easily able to wire "events" coming through kafka to methods of the microservice that should be executed
- A simple API for raising events from within a microservice
- Microservice reachable via rest api
