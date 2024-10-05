# Usa un'immagine di base con JRE
#FROM openjdk:17-jdk-alpine

# Usa una base di JRE 17 leggera (Ad esempio, Eclipse Temurin)
FROM eclipse-temurin:17-jre-alpine

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file JAR nel container
COPY preventivo2-1.jar /app/preventivo2-1.jar

# Espone la porta su cui l'applicazione sarà in ascolto
EXPOSE 8095

# Comando per eseguire l'applicazione
ENTRYPOINT ["java", "-jar", "/app/preventivo2-1.jar"]
