FROM maven:3.9.11-eclipse-temurin-21 AS builder
WORKDIR /src
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q -Dtest=JwtSecurityTest,KeyStorageSecurityTest,AccessSecurityTest,SQLiteRuntimeTest verify

# Extract the matching native library into the immutable image. Runtime SQLite
# loading must not depend on extracting an executable .so under a tmpfs mount.
RUN set -eu; \
    case "$(uname -m)" in aarch64|arm64) SQLITE_ARCH=aarch64 ;; x86_64|amd64) SQLITE_ARCH=x86_64 ;; *) exit 1 ;; esac; \
    mkdir -p /src/sqlite-native; \
    cd /src/sqlite-native; \
    jar xf /root/.m2/repository/org/xerial/sqlite-jdbc/3.49.1.0/sqlite-jdbc-3.49.1.0.jar "org/sqlite/native/Linux/$SQLITE_ARCH/libsqlitejdbc.so"; \
    cp "org/sqlite/native/Linux/$SQLITE_ARCH/libsqlitejdbc.so" ./libsqlitejdbc.so; \
    chmod 755 ./libsqlitejdbc.so

FROM eclipse-temurin:21-jre-jammy
RUN apt-get update && apt-get install -y --no-install-recommends openssh-client curl lsof \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd -g 10001 ocihelper && useradd -u 10001 -g 10001 -m ocihelper \
    && mkdir -p /app/oci-helper/keys /app/oci-helper/logs /home/ocihelper/.ssh \
    && chown -R 10001:10001 /app/oci-helper /home/ocihelper/.ssh \
    && chmod 700 /home/ocihelper/.ssh
COPY --from=builder /src/target/oci-helper-3.5.1.jar /opt/oci-helper.jar
COPY --from=builder /src/sqlite-native/libsqlitejdbc.so /opt/sqlite-native/libsqlitejdbc.so
ENV LANG=C.UTF-8 TZ=America/Montevideo OCI_HELPER_VERSION=3.5.1-es2
WORKDIR /app/oci-helper
USER 10001:10001
RUN java -Dorg.sqlite.lib.path=/opt/sqlite-native -Dorg.sqlite.lib.name=libsqlitejdbc.so \
    -Dloader.main=com.yohann.ocihelper.utils.SQLiteNativeSmoke \
    -cp /opt/oci-helper.jar org.springframework.boot.loader.launch.PropertiesLauncher
EXPOSE 8818
ENTRYPOINT ["java","-Dorg.sqlite.lib.path=/opt/sqlite-native","-Dorg.sqlite.lib.name=libsqlitejdbc.so","--add-opens","java.base/java.net=ALL-UNNAMED","--add-opens","java.base/sun.net.www.protocol.https=ALL-UNNAMED","-jar","/opt/oci-helper.jar"]
