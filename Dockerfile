FROM openjdk:17
WORKDIR /home/tm
COPY target/tm2021-lab2-1.0-jar-with-dependencies.jar /tm2021.jar
CMD ["java","-cp","/tm2021.jar","tm2021.fcul.node.NodeProjeto"]