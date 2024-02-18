# Tools Microservice

## Prerequisites

### Node 18
For Windows
```bsh
choco install node
```
For MacOS
```bsh
brew install node
```
### Populate Postgres with data

```bsh
brew update
brew install postgresql
brew services start postgresql
brew install postgresql-client
```

```bsh
# Source the environment variables
source .env.local
# Run the catalog schema file
psql $POSTGRES_URI -f catalog_schema.sql
# Run the catalog data file
psql $POSTGRES_URI -f catalog_data.sql
```

## Build
```bsh
npm install
npm install -g nodemon
```

## Run Locally
```bsh
npm start
```

## Build Docker Image

Tell Docker CLI to talk to minikube's VM.

For MacOS,
`eval $(minikube docker-env)`
For Windows,
`& minikube -p minikube docker-env --shell powershell | Invoke-Expression`

Build docker image,
`docker build -t tools:latest .`
