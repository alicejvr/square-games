# 🎮 Square Games API

API REST développée avec **Java** et **Spring Boot** permettant de créer et gérer des parties de jeux.

L'application prend en charge plusieurs types de jeux grâce à un système de **plugins**, et utilise une base de données pour assurer la persistance des parties.

> 📚 Projet réalisé dans le cadre d'une formation en développement web / Java.

---

## 🛠️ Technologies utilisées

* ☕ Java
* 🌱 Spring Boot
* 🌐 Spring Web
* 🗄️ Spring Data JPA
* 🐬 MySQL
* 📖 Springdoc OpenAPI / Swagger
* 🧪 Bruno pour les tests de l'API
* 📦 Maven

---

## 🏗️ Architecture

L'application est organisée autour de plusieurs couches :

```text
Client HTTP (Bruno / Postman)
          │
          ▼
    REST Controller
          │
          ▼
      GameService
          │
     ┌────┴────┐
     ▼         ▼
GamePlugin   GameDao
     │         │
     ▼         ▼
Game Factory  JPA
               │
               ▼
            MySQL
```

L'application communique également avec le service **api-user** afin de vérifier l'existence des utilisateurs avant certaines opérations.

```text
┌──────────────────────┐
│     square-games     │
│      :8080           │
└──────────┬───────────┘
           │
           │ HTTP
           ▼
┌──────────────────────┐
│       api-user       │
│        :8081         │
└──────────────────────┘
```

---

## 🎲 Jeux disponibles

L'API utilise un système de plugins permettant de gérer plusieurs jeux.

| Jeu         | Identifiant | Joueurs | Plateau |
| ----------- | ----------- | ------: | ------: |
| Tic-Tac-Toe | `tictactoe` |       2 |   3 à 5 |
| Puissance 4 | `connect4`  |       2 |       7 |
| Taquin      | `15 puzzle` |       1 |       4 |

---

# 🚀 Installation

## Prérequis

Avant de démarrer l'application, installer :

* Java
* Maven
* MySQL
* Git

Il est également nécessaire d'avoir lancé l'application **api-user** si l'on souhaite utiliser les fonctionnalités nécessitant la validation des utilisateurs.

---

## 📥 Cloner le projet

```bash
git clone <URL_DU_REPOSITORY>
cd square-games
```

---

## 🗄️ Configuration de la base de données

Créer une base de données MySQL pour l'application.

Puis renseigner les paramètres de connexion dans :

```text
src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/square_games
spring.datasource.username=VOTRE_UTILISATEUR
spring.datasource.password=VOTRE_MOT_DE_PASSE

spring.jpa.hibernate.ddl-auto=update
```

Adaptez les valeurs à votre environnement local.

> ⚠️ Ne publiez jamais un mot de passe ou une information sensible dans un dépôt GitHub public.

---

## ⚙️ Configuration de api-user

`square-games` utilise l'API `api-user` pour vérifier qu'un utilisateur existe.

Dans :

```text
src/main/resources/application.properties
```

configurer :

```properties
user-service.url=http://localhost:8081
```

L'application `api-user` doit donc être démarrée sur le port **8081**.

---

# ▶️ Démarrer l'application

Depuis la racine du projet :

```bash
mvn spring-boot:run
```

L'application démarre sur :

```text
http://localhost:8080
```

---

# 📖 Documentation Swagger

Une documentation interactive des endpoints est disponible avec Swagger.

Une fois l'application démarrée :

**Swagger UI :**

```text
http://localhost:8080/swagger-ui.html
```

Elle permet notamment de consulter les endpoints et de tester les requêtes directement depuis le navigateur.

---

# 🔌 Endpoints principaux

## 🎮 Créer une partie

```http
POST /games
```

Header :

```text
X-UserId: <UUID_UTILISATEUR>
```

Exemple :

```json
{
  "gameType": "tictactoe",
  "numberOfPlayers": 2,
  "boardSize": 3,
  "opponentIds": [
    "8f2a6c11-5d43-4b7e-91c2-36a8f0472d19"
  ]
}
```

---

## 📋 Récupérer toutes les parties

```http
GET /games
```

---

## 👤 Récupérer les parties d'un utilisateur

```http
GET /gamesForUser
```

Header :

```text
X-UserId: <UUID_UTILISATEUR>
```

---

## 🔎 Récupérer une partie

```http
GET /games/{gameId}
```

Header :

```text
X-UserId: <UUID_UTILISATEUR>
```

---

## ♟️ Récupérer les mouvements possibles

```http
GET /games/{gameId}/tokens/{tokenId}/moves
```

Header :

```text
X-UserId: <UUID_UTILISATEUR>
```

---

## 🎯 Jouer un mouvement

```http
POST /games/{gameId}/moves
```

Header :

```text
X-UserId: <UUID_UTILISATEUR>
```

Exemple de corps :

```json
{
  "x": 1,
  "y": 2
}
```

---

# 🧪 Tester avec Bruno

Les requêtes de test sont disponibles dans la collection Bruno du projet.

Elles permettent notamment de tester :

* la création d'une partie ;
* la récupération des parties ;
* la récupération d'une partie précise ;
* la récupération des mouvements possibles ;
* l'exécution d'un mouvement ;
* la validation des utilisateurs.

Les requêtes peuvent utiliser des **variables** afin de réutiliser automatiquement les identifiants entre plusieurs appels.

---

# 🔐 Validation des utilisateurs

Avant certaines opérations, `square-games` vérifie que l'utilisateur existe dans l'application `api-user`.

Le fonctionnement est le suivant :

```text
square-games
     │
     │ GET /users/{id}/valid
     ▼
 api-user
     │
     ├── true  → requête autorisée
     │
     └── false → HTTP 403
```

Les deux applications doivent donc être démarrées pour utiliser complètement l'API.

---

# 📁 Structure du projet

```text
src/
└── main/
    ├── java/
    │   └── com.square_games.demo/
    │       ├── controller/
    │       ├── service/
    │       ├── dao/
    │       ├── entity/
    │       ├── plugin/
    │       └── ...
    │
    └── resources/
        ├── application.properties
        └── messages.properties
```

---

# 👩‍💻 Projet

Projet réalisé dans le cadre d'une formation de développement web.

Technologies principales : **Java · Spring Boot · REST API · JPA · MySQL · Swagger**
