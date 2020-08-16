# Leader Board Platform
The leaderboard platform API is designed to create leader board among users to its scores and ranked each other.


[![Issues][issues-shield]][issues-url]


<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/ayciceksamet/leaderboardplatform/blob/master/README.md">
    <img src="https://camo.githubusercontent.com/d92c579684f6c4040ba5e1ae91c80fb95aa1ff59/68747470733a2f2f70726f6772616d6d6572667269656e642e636f6d2f696d672f636f6e74656e742f726f627573742d626f6f745f7469746c652e706e67" alt="Logo" width="500" height="150">
    <img src="https://upload.wikimedia.org/wikipedia/commons/5/5c/AWS_Simple_Icons_AWS_Cloud.svg" alt="Logo-AWS" width="200" height="150">
    
  </a>

  <h3 align="center">Leader Board Platform API</h3>

  <p align="center">
    The API is designed to integrate your application with score ranking among the users
    <br />
    <a href="https://github.com/ayciceksamet/leaderboardplatform/blob/master/README.md"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="http://18.184.175.45:8080/leaderboard">View Demo</a>
    ·
    <a href="https://github.com/ayciceksamet/leaderboardplatform/issues">Report Bug</a>
    ·
    <a href="https://github.com/ayciceksamet/leaderboardplatform/issues">Request Feature</a>
  </p>
</p>


<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Project Structure](#the-project-structure)
  * [API Documentation](#api-documentation)
  * [Prerequisites](#prerequisites)
* [Usage](#usage)
  * [Try on deployed AWS EC2 Server](#the-steps-to-try-demo-on-deployed-aws-ec2-server)
  * [Try on your own server using Dockerized Image](#the-steps-to-try-on-your-server-using-dockerized-image)
* [Contributing](#contributing)
* [Contact](#contact)


<!-- ABOUT THE PROJECT -->
## About The Project

The API is created to achieve leaderboard ranking among the users to their scores. You can submit new scores the specified user and see the ranking changes.


### Built With
This section should list any major frameworks that you built your project using. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Redis](https://redis.io/)
* [Lombok](https://projectlombok.org/)
* [Swagger](https://swagger.io/)
* [Amazon EC2](https://aws.amazon.com/tr/ec2/)



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally and run on AWS EC2 instance.

### The Project Structure

 <img src="https://github.com/ayciceksamet/leaderboardplatform/blob/master/1.PNG" alt="Logo-structure" width="400" height="400">


### API Documentation

http://18.184.175.45:8080/swagger-ui.html#/ranking-score-controller

<img src="https://github.com/ayciceksamet/leaderboardplatform/blob/master/3.PNG" alt="Logo-structure" width="1200" height="400">


To check the system is working 

(GET)
http://18.184.175.45:8080/healthcheck 


To submit bulk user to platform with random names, scores and countries

(POST)
http://18.184.175.45:8080/user/bulkcreate/50


To retrieve leaderboard from platform

(GET)
http://18.184.175.45:8080/leaderboard


To add new user to platform

(POST)
http://18.184.175.45:8080/user/create/

```
{
"displayName": "BABO",
"points" : 1500,
"rank":1,
"country": "tr"
}

```

To add score to specific user 

(POST)
http://18.184.175.45:8080/score/submit/
```
{
"score_worth": "550000000000",
"user_id" : "c1c64f48-e7b0-4f5d-8bff-573eebb3bdf5"
}
```

To retrieve specific country iso code with leaderboard

(GET) http://18.184.175.45:8080/leaderboard/tr

To retrieve specific user with user id

(GET) http://18.184.175.45:8080/user/profile/c1c64f48-e7b0-4f5d-8bff-573eebb3bdf5




### Prerequisites

This is an example of how to list things you need to use the software and how to install them.

The Redis server is running on AWS EC2 server and it is configured to host name in project. You should change this parameter from application.properties if you want to run on antoher server with redis. They are communicating in each other. You should deploy the docker image on your server and the redis server will be served on specified AWS EC2 server.

The Leader Board API Platform <===> Redis Server

**spring.redis.host = ec2-18-184-175-45.eu-central-1.compute.amazonaws.com**

 <img src="https://github.com/ayciceksamet/leaderboardplatform/blob/master/2.png" alt="Logo-structure" width="400" height="80">


* Docker and Redis Server
```sh
sudo apt-get install docker-ce docker-ce-cli containerd.io
```

```sh
sudo apt install redis-server
```
## Usage


### The steps to try demo on deployed AWS EC2 Server

1. Create amount of user with using bulk creation endpoint. This will generate random users and their data on redis server running on AWS EC2 Server.

(POST)
http://18.184.175.45:8080/user/bulkcreate/50

2. You can access the leaderboard using this endpoint.

(GET)
http://18.184.175.45:8080/leaderboard

And also you could use other endpoints as well.

### The steps to try on your server using Dockerized Image

1. Dowload the docker image of project [The Docker Image Container on Cloud](https://drive.google.com/file/d/1VTxVqt3RfW3vLse5ygMy5xm7B74n5ROx/view?usp=sharing)

2. Upload to your server the docker image

3. Load the image on your server

```sh
docker load -i rankingplatformdockerimage
```
3. Serve the image using this command on 8080 port
```sh
docker run -t -d -p 8080:8080 rankingplatformdockerimage
```

4. Please check with health check endpoint on your server.
```sh
http://18.184.175.45:8080/healthcheck 
```



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request





<!-- CONTACT -->
## Contact

ayciceksamet 

Project Link: [https://github.com/ayciceksamet/leaderboardplatform](https://github.com/ayciceksamet/leaderboardplatform)


