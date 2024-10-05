# Usa un'immagine di base con JRE
FROM openjdk:17-jdk-alpine

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file JAR nel container
COPY target/preventivo2-1.jar /app/preventivo2-1.jar

# Espone la porta su cui l'applicazione sarà in ascolto
EXPOSE 8080

# Comando per eseguire l'applicazione
ENTRYPOINT ["java", "-jar", "/preventivo2-1.jar"]
