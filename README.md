# 🎮 Square Games

> Application web de jeux de plateau développée avec **Java et Spring Boot**.

**Square Games** permet de créer et gérer des parties de plusieurs jeux, de gérer les joueurs et leurs mouvements, et propose une interface web avec **Thymeleaf**.

Le projet fonctionne avec une seconde application, **api-user**, dédiée à la gestion des utilisateurs et à l'authentification.

---

## 🏗️ Architecture

Le projet est composé de **deux applications Spring Boot indépendantes** qui communiquent via HTTP/REST.

```text
                         🌐 Navigateur
                              │
                              ▼
                ┌─────────────────────────┐
                │      🎮 square-games    │
                │        Port 8080        │
                │                         │
                │  🎲 Jeux & parties      │
                │  👥 Joueurs             │
                │  🔌 API REST            │
                │  🖥️ Interface Thymeleaf │
                │  🔐 JWT                 │
                └────────────┬────────────┘
                             │
                          HTTP/REST
                             │
                             ▼
                ┌─────────────────────────┐
                │       👤 api-user       │
                │        Port 8081        │
                │                         │
                │  👤 Utilisateurs        │
                │  🔐 Authentification    │
                │  🎟️ JWT                │
                └────────────┬────────────┘
                             │
                             ▼
                           🗄️ MySQL
```

### 🎯 Responsabilités de `square-games`

* 🎲 Gestion des jeux
* 🎮 Création et gestion des parties
* 👥 Gestion des joueurs
* 🎯 Gestion des mouvements
* 💾 Persistance des parties
* 🌐 API REST
* 🖥️ Interface web Thymeleaf
* 🔐 Vérification des JWT

### 👤 Responsabilités de `api-user`

* 👤 Gestion des utilisateurs
* 🔑 Authentification
* 🛡️ Gestion des rôles
* 🎟️ Génération des JWT
* ✅ Vérification des utilisateurs

---

## 🛠️ Technologies

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot\&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?logo=springsecurity\&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql\&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf\&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?logo=bootstrap\&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven\&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?logo=jsonwebtokens\&logoColor=white)

---

## 🎲 Jeux disponibles

L'application utilise une architecture basée sur des **plugins**, permettant d'intégrer différents jeux.

| Jeu                             | Identifiant | Joueurs |
| ------------------------------- | ----------- | ------: |
| ❌⭕ Morpion / Tic-Tac-Toe        | `tictactoe` |       2 |
| 🔴🟡 Puissance 4 / Connect Four | `connect4`  |       2 |
| 🔢 Taquin                       | `15 puzzle` |       1 |

Chaque jeu possède son propre `GamePlugin`.

Cette architecture permet d'ajouter de nouveaux jeux sans modifier le fonctionnement général du service de gestion des parties.

---

## 🔐 Authentification

L'authentification est gérée par **api-user**.

Le fonctionnement est le suivant :

```text
👤 Utilisateur
      │
      │ Identifiant + mot de passe
      ▼
🎮 square-games
      │
      │ POST /auth/login
      ▼
👤 api-user
      │
      │ Vérification
      │
      │ Génération du JWT
      ▼
🎮 square-games
      │
      │ JWT
      ▼
🍪 Cookie dans le navigateur
      │
      ▼
🔐 JwtAuthenticationFilter
      │
      ▼
✅ Utilisateur authentifié
```

Le JWT contient notamment :

* 👤 le nom de l'utilisateur ;
* 🛡️ son rôle ;
* 🕐 sa date d'émission ;
* ⏳ sa date d'expiration.

---

## 🖥️ Interface web

L'interface est réalisée avec :

* **Thymeleaf**
* **Bootstrap**
* HTML/CSS

Les templates sont situés dans :

```text
src/main/resources/templates/
```

Pages actuelles :

```text
templates/
├── login.html
└── games-home.html
```

### 🔑 Connexion

```text
GET /
```

affiche la page de connexion.

Le formulaire envoie les identifiants vers :

```text
POST /login
```

Après authentification, l'utilisateur est redirigé vers :

```text
GET /games-home
```

La page utilise notamment Thymeleaf pour afficher les informations de l'utilisateur connecté.

---

## 🌐 API REST

### 🎮 Récupérer les parties

```http
GET /games
```

Retourne les parties associées à l'utilisateur authentifié.

Exemple :

```json
[
  "25c4f297-0b58-41fd-9d96-5190ad12f88b",
  "41859da4-52f5-4ff3-b8d3-c0c3c415064c"
]
```

### ➕ Créer une partie

```http
POST /games
```

### 🔎 Récupérer une partie

```http
GET /games/{gameId}
```

### 🎯 Jouer un mouvement

```http
POST /games/{gameId}/moves
```

Exemple de corps de requête :

```json
{
  "x": 1,
  "y": 2
}
```

Le serveur vérifie notamment que l'utilisateur authentifié est autorisé à effectuer le mouvement.

---

## 💾 Persistance

Les parties sont persistées dans **MySQL** grâce à :

* Spring Data JPA
* Hibernate
* JPA

Principales classes :

```text
GameDao
   │
   ▼
JpaGameDao
   │
   ▼
GameEntityRepository
   │
   ▼
MySQL
```

Les principales entités concernent :

* 🎮 les parties ;
* 👥 les joueurs ;
* 🧩 les tokens / pions.

---

## 📁 Structure du projet

```text
square-games/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com.square_games.demo/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── plugin/
│       │       ├── service/
│       │       └── ...
│       │
│       └── resources/
│           ├── templates/
│           │   ├── login.html
│           │   └── games-home.html
│           │
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🚀 Installation et démarrage

### 📋 Prérequis

* ☕ Java
* 📦 Maven
* 🗄️ MySQL
* 👤 l'application `api-user`

### 1️⃣ Démarrer `api-user`

L'application doit être disponible sur :

```text
http://localhost:8081
```

Voir le README du projet `api-user`.

### 2️⃣ Démarrer `square-games`

Dans le dossier du projet :

```bash
mvn spring-boot:run
```

L'application démarre sur :

```text
http://localhost:8080
```

### 3️⃣ Ouvrir l'application

Dans le navigateur :

```text
http://localhost:8080/
```

---

## 🧪 Tester l'API

Les requêtes REST peuvent être testées avec **Bruno**.

Exemples :

```text
GET  /games
POST /games
GET  /games/{gameId}
POST /games/{gameId}/moves
```

---

## 🔒 Configuration et données sensibles

Les informations de connexion à la base de données sont configurées dans :

```text
src/main/resources/application.properties
```

⚠️ Les mots de passe et autres informations sensibles ne doivent pas être publiés sur GitHub.

---

## 🔗 Projet associé

👤 **api-user**

Service dédié aux utilisateurs et à l'authentification.

---

## 👩‍💻 Projet

Projet réalisé dans le cadre d'une formation en développement web.

**Java • Spring Boot • REST • JPA • MySQL • JWT • Thymeleaf • Bootstrap**
