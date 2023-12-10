# Url shortener Service
Sample service to provide simple url shortening.
- `save url`
- `get full url`
- `resolve shortened url`

### Run locally
#### From command line
1. Copy ``.env.example`` to ``.env`` - optional
2. Change values inside ``.env`` - optional
3. ```docker-compose up```
4. ```./gradlew bootRun```

Or after ```docker-compose up``` run app from your favourite IDE.

#### Swagger
Application API is accessible from [swagger ui](http://localhost:8080/url-shortener/internal_api/swagger-ui/index.html)

### Run test locally

To run tests locally ``redis`` must be up and running:
``docker-compose up``

### Not implemented features / future improvements
- `security`
- `url validation`
- `hash colisions`
- `other hashing methods`
- `different storage e.g. AWS DynamoDB`
- `shortening addresses only for a given domain, e.g.: com.example/very-long-url?param=param1 -> com.example/bac8a6be`