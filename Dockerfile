# base image - an image with openjdk  16
FROM openjdk:16

# working directory inside docker image
WORKDIR /home/tm

# copy the jar created by assembly to the docker image
COPY target/*jar-with-dependencies.jar tm2021.jar

# run Discovery when starting the docker image
CMD ["java", "-cp", "/home/tm/tm2021.jar", "tm2021.lab2.server.UsersServer"]
