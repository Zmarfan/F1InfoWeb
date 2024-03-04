# F1InfoWeb

This project utilizes the now deprecated *(which is why this project never got finalized and released)* [**Ergast Api**](https://ergast.com/mrd/). This API allowed for fetching of Formula 1 data such as race results, championship standings, circuit information, lap times etc. The goal of the project was to create a website where users can look up statistics for certain years, drivers, races and so on. Furthermore the project served as a learning experience in using [**Spring Boot**](https://spring.io/projects/spring-boot), [**Angular**](https://angular.io/) and [**MySQL**](https://www.mysql.com/). The website was hosted using [**AWS**](https://aws.amazon.com) during development.

The website allows for secure custom login and password handling with email verification to allow users to leave feedback and make friends. The configuration that can be found in the repo is merly a mock configuration and for actual deployment this is switched out. It also has a great deal of unit tests in the server to verify correctness. This project never reached a finalized state due to the annoncement of the deprication of the Ergast API which most of the functionality relied on. The backend server fetched data daily from the API in regulation with the rate-limits and stored copies of the data in the MySQL database as to allow for faster response time for users as well as less load on the Ergast API. Additional data was fetched from [Wikipedia](https://www.wikipedia.org/).

Here are some videos and images created during development of the project:

## Video 1
[![Video 1](https://img.youtube.com/vi/JoAU1E8UlXs/0.jpg)](https://www.youtube.com/watch?v=JoAU1E8UlXs)

## Video 2
[![Video 2](https://img.youtube.com/vi/V0lUO68tjZU/0.jpg)](https://www.youtube.com/watch?v=V0lUO68tjZU)

## Images

![pic-1](https://github.com/Zmarfan/F1InfoWeb/blob/master/Readme/image-1.jpg?raw=true)
![pic-2](https://github.com/Zmarfan/F1InfoWeb/blob/master/Readme/image-2.jpg?raw=true)
![pic-3](https://github.com/Zmarfan/F1InfoWeb/blob/master/Readme/image-3.jpg?raw=true)
