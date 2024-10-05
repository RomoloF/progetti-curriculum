#!/bin/bash

# Directory in cui si trovano i JAR
APP_DIR="/home/romolofiorenza/APPLICAZIONI_CREATE"

# Nomi dei file JAR
APP1_JAR="serverRestful.jar"
APP2_JAR="jerseyClient.jar"

# Directory dei log
LOG_DIR="$APP_DIR/logs"

# Creazione della directory dei log se non esiste
mkdir -p "$LOG_DIR"

# Comandi per avviare le applicazioni
start_app1() {
    echo "Starting app1...serverRestful"
    nohup java -jar "$APP_DIR/$APP1_JAR" > "$LOG_DIR/app1.log" 2>&1 &
    echo "app1 started with PID $!"
}

start_app2() {
    echo "Starting app2...jerseyClient"
    nohup java -jar "$APP_DIR/$APP2_JAR" > "$LOG_DIR/app2.log" 2>&1 &
    echo "app2 started with PID $!"
}

# Avvia entrambe le applicazioni
start_app1
start_app2

# Verifica se le applicazioni sono state avviate correttamente
ps aux | grep -e "$APP1_JAR" -e "$APP2_JAR"

echo "Le applicazioni sono in esecuzione in background."
echo "Bisogna aspettare che si avviano le applicazioni."
echo "Il server API sta in ascolto sulla porta 8085 e risponde con SWAGGER"
echo "Il server Client sta in ascolto sulla porta 8080 e risponde alla index"

# Funzione per mostrare i processi attivi delle app
show_processes() {
    echo "Showing processes for app1 and app2..."
    echo "Processes for app1 (jar: $APP1_JAR):"
    ps aux | grep "$APP1_JAR" | grep -v grep
    echo "Processes for app2 (jar: $APP2_JAR):"
    ps aux | grep "$APP2_JAR" | grep -v grep
}

# Funzione per fermare le app
stop_apps() {
    echo "Stopping app1 and app2..."
    pkill -f "$APP1_JAR"
    pkill -f "$APP2_JAR"
    echo "Both apps stopped."
}
# Gestione degli argomenti dello script
case "$1" in
    start)
        start_and_show
        echo "Both applications are running in the background."
        echo "Logs are being written to $LOG1_FILE and $LOG2_FILE."
        ;;
    stop)
        stop_apps
        ;;
    status)
        show_processes
        ;;
    *)
        echo "Usage: $0 {start|stop|status}"
        exit 1
        ;;
esac



