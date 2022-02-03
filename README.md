# S'banken

## Frontend

Implemented in vue

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run dev
```

### Compiles and minifies for production
```
npm run build
```

## Backend

Run SbankenApplication spring boot application

## Build

```
mvn clean install
```

* frontend-maven-plugin - builds vue to vue/dist
* maven-antrun-plugin - copy vue/dist to target/classes/public

```
docker build -t sbanken:latest .
```

## Run

```
docker run -d -e CLIENT_ID=... -e CLIENT_SECRET=... -p 8080:8080 --rm sbanken:latest
```
