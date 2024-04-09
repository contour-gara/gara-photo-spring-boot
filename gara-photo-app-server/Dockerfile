FROM amazoncorretto:17.0.10-al2023 AS build
WORKDIR /jlink
RUN jlink \
    --verbose \
    --compress=2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --add-modules java.base,java.desktop,java.instrument,java.net.http,java.prefs,java.scripting,java.security.jgss,java.security.sasl,java.sql.rowset,jdk.attach,jdk.compiler,jdk.jdi,jdk.jfr,jdk.management,jdk.unsupported,jdk.localedata,java.compiler,java.naming \
    --output jre-min

FROM amazonlinux:2023.3.20240219.0
WORKDIR /deployments
COPY --from=build /jlink/jre-min /opt/jre-min
ENV JAVA_HOME=/opt/jre-min
ENV PATH $JAVA_HOME/bin:$PATH
ARG JAR_FILE=build/libs/gara-photo-app-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
