# Dockerfile for Continius-Integration project

FROM anapsix/alpine-java:8

# Copy project into image
COPY . /app

# Build project
RUN app/gradlew build

CMD java -classpath ./app/out/production/classes se.kth.dd2480.grp25.ci.CiServer
