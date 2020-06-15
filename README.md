## Lancement

Pour lancer le serveur avec Maven :

```console
$ mvn spring-boot:run
```

Pour lancer le serveur avec Java :

```console
$ mvn package
$ java -jar target/server-version.jar
```

Pour lancer le serveur avec Docker :

```console
$ docker build . --tag server
$ docker run -d -p 8080:8080 --name="server" server
```

## Configurer ses variables d'environnements sur IntelliJ
- Ouvrir la fenêtre 'Edit Run/Debug Configuration'.
- Se rendre dans la configuration du launcher Spring boot.
- Dans l'onglet 'Configuration' ajouter les variables d'environnements suivantes

```
AUTHENTIFICATION_DATABASE=admin
DB_USERNAME=root
DB_PASSWORD=c---------------s
DB_NAME=jee-server
DB_PORT=27010
DB_HOST=51.178.18.199
```