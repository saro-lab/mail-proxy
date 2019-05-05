# mail-proxy
saro mail proxy is mail proxy micro-service.
It is a micro-service for separation from main application.
currently, only smtp is supported.


## step 1 : execute
### basic
- default port 8080
```
java -jar mail-proxy.jar
```
### specify the server port
```
java -jar mail-proxy.jar --server.port=80
```

## step 2 : register auth
### auth check
```
curl localhost:8080/auth/testAuthId
```
```
{"code":"NOT_FOUND","msg":"id not found, please check id or register id","data":null}
```
### get register template
```
curl localhost:8080/auth/template
```
```
{"code":"OK","msg":"auth template","data":{"id":"id for using in the mail-proxy","host":"host","port":0,"mail":"mail address","user":"username","pass":"password"}}
```
### register (ex google)
```
# in general
curl -X POST localhost:8080/auth -H "Content-Type: application/json" -d '{"id":"smtp-google-test","host":"smtp.gmail.com","port":465,"mail":"test@saro.me","user":"test@saro.me","pass":"password"}'
# windows (using windows curl) : '" issue'
curl -X POST localhost:8080/auth -H "Content-Type: application/json" -d "{\"id\":\"smtp-google-test\",\"host\":\"smtp.gmail.com\",\"port\":465,\"mail\":\"test@saro.me\",\"user\":\"test@saro.me\",\"pass\":\"password\"}"
```
```
{"code":"OK","msg":null,"data":null}
```
### checking for the booked id
```
curl localhost:8080/auth/smtp-google-test
```
```
{"code":"OK","msg":"","data":{"id":"smtp-google-test","host":"smtp.gmail.com","port":465,"mail":"test@saro.me","user":"test@saro.me","pass":"****"}}
```

## step 2 : using
...
