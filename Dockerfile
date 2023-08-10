FROM openjdk:17-alpine
ADD /build/libs/petClinic-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]
# docker build -t image_name ./   to name image for docker build