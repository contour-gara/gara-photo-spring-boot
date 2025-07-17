FROM amazoncorretto:21.0.8-al2023 AS build

# カスタム JRE
WORKDIR /jlink
RUN jlink \
    --verbose \
    --compress=2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --add-modules java.base,java.compiler,java.desktop,java.instrument,java.net.http,java.prefs,java.rmi,java.scripting,java.security.jgss,java.security.sasl,java.sql.rowset,jdk.attach,jdk.jdi,jdk.jfr,jdk.management,jdk.unsupported,jdk.crypto.ec \
    --output jre-min

# CDS
WORKDIR /cds
ARG JAR_FILE=build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=tools -jar app.jar extract --destination app
RUN java -Dspring.profiles.active=cds -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=app/app.jsa -jar app/app.jar

FROM debian:bookworm-20250113-slim

WORKDIR /deployments
RUN apt-get update -y && apt-get install -y curl
COPY --from=build /jlink/jre-min /opt/jre-min
ENV JAVA_HOME=/opt/jre-min
ENV PATH $JAVA_HOME/bin:$PATH
ENV LANG C.UTF-8
ARG JAR_FILE=/cds/app/app.jar
COPY --from=build ${JAR_FILE} app.jar
ARG JSA_FILE=/cds/app/app.jsa
COPY --from=build ${JSA_FILE} app.jsa
ARG LIB=/cds/app/lib
COPY --from=build ${LIB} lib

ENTRYPOINT ["java", "-XX:SharedArchiveFile=app.jsa", "-jar", "app.jar"]
