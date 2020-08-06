FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG ORA_WALLET=src/main/resources/static/OraWallet
#RUN mkdir -p /OraWallet
COPY ${ORA_WALLET} /OraWallet/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "-Doracle.net.tns_admin=/OraWallet", "-Doracle.net.wallet_location=/OraWallet"]
