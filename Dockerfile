#Dockerfile for Continius-Integration project
FROM gradle:5.2-jdk-alpine

# Copy project into image
WORKDIR /srv
COPY build.gradle .
COPY src ./src
USER root
RUN  apk update && apk upgrade && apk add --no-cache bash git openssh

CMD ["gradle", "build  --console plain"]
