## About
Uber is a personal project built Java and Springboot Framework technologies.Project represents model of a "uber" 
application that can find nearest driver near you and schedule a trip.
It mainly uses SQL database, but is also empowered
with in-memory database such as Redis to manage high variable I/O.
Uses Hibernate as JPA implementation and it's Criteria API for custom qureis.
Database Schema creation is achieved through Flyway migrations.

## How to run it

**Run in local-host env:**
Application requries MySQL database that can be configured through properties. 
After which will try to connect to Redis server instance on default port (6379).

**Run it on Heroku:**
Project is also available for testintg purposes on Heroku servers.[Heroku](euber-test.herokuapp.com)
*(Be awere that it is running on free instance and can take up to 20 seconds to load up server)*

*Frontend part is still in developing phase and can be seen in template dir* 




