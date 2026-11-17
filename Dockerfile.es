FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /src
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q -Dtest=JwtSecurityTest,KeyStorageSecurityTest,AccessSecurityTest verify

FROM eclipse-temurin:21-jre-jammy
RUN apt-get update && apt-get install -y --no-install-recommends openssh-client curl lsof \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd -g 10001 ocihelper && useradd -u 10001 -g 10001 -m ocihelper \
    && mkdir -p /app/oci-helper/keys /app/oci-helper/logs /home/ocihelper/.ssh \
    && chown -R 10001:10001 /app/oci-helper /home/ocihelper/.ssh \
    && chmod 700 /home/ocihelper/.ssh
COPY --from=builder /src/target/oci-helper-3.5.1.jar /opt/oci-helper.jar
ENV LANG=C.UTF-8 TZ=America/Montevideo OCI_HELPER_VERSION=3.5.1-es1
WORKDIR /app/oci-helper
USER 10001:10001
EXPOSE 8818
ENTRYPOINT ["java","--add-opens","java.base/java.net=ALL-UNNAMED","--add-opens","java.base/sun.net.www.protocol.https=ALL-UNNAMED","-jar","/opt/oci-helper.jar"]
