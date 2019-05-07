# mail-proxy
saro mail proxy is mail proxy micro-service.
It is a micro-service for separation from main application.
currently, only smtp is supported.

## prepare
download : [releases page](https://github.com/saro-lab/mail-proxy/releases)

need to : jdk version 11 or later

## execute
### basic
- default port 8080
```
java -jar mail-proxy.jar
```
### specify the server port
```
java -jar mail-proxy.jar --server.port=80
```

## register auth
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
{"code":"OK","msg":"auth template","data":{"id":"id for using in the mail-proxy","host":"host","port":0,"mail":"sender email address","display":"sender display","user":"username","pass":"password"}}
```
### register (ex google)
```
# in general
curl -X POST localhost:8080/auth -H "Content-Type: application/json" -d '{"id":"smtp-google-test","host":"smtp.gmail.com","port":465,"display":"saro tester","mail":"test@saro.me","user":"test@saro.me","pass":"password"}'
```
```
{"code":"OK","msg":null,"data":null}
```
### checking for the booked id
```
curl localhost:8080/auth/smtp-google-test
```
```
{"code":"OK","msg":"","data":{"id":"smtp-google-test","host":"smtp.gmail.com","port":465,"mail":"test@saro.me","display":"saro tester","user":"test@saro.me","pass":"****"}}
```

## SMTP
### get smtp template
```
curl localhost:8080/smtp/template
```
```
{"code":"OK","msg":"auth template","data":{"id":"registered auth id","to":[{"display":"display","mail":"email"},{"display":"display","mail":"email"}],"cc":[{"display":"display","mail":"email"},{"display":"display","mail":"email"}],"subject":"email subject","content":"html content"}}
```
### send
```
# in general
curl -X POST "localhost:8080/smtp/send" -H "Content-Type: application/json" -d '{"id":"smtp-google-test","to":[{"display":"foo","mail":"foo@test.com"},{"display":"abc manager/test","mail":"manager@text.com"}],"cc":[{"display":"foo bar","mail":"foooo@test.com"}],"subject":"hi!","content":"<h1>html content</h1>"}'
```
```
{"code":"OK","msg":"","data":null}
```
### send all
```
# it's same send, just json object -> json array
# for example
curl -X POST "localhost:8080/smtp/send" -H "Content-Type: application/json" -d '[{"id":"smtp-google-test","to":[{"display":"foo","mail":"foo@test.com"},{"display":"abc manager/test","mail":"manager@text.com"}],"cc":[{"display":"foo bar","mail":"foooo@test.com"}],"subject":"hi!","content":"<h1>html content</h1>"},{"id":"smtp-google-test","to":[{"display":"foo","mail":"foo@test.com"},{"display":"abc manager/test","mail":"manager@text.com"}],"cc":[{"display":"foo bar","mail":"foooo@test.com"}],"subject":"hi! 2","content":"<h1>html content</h1>"}]'
```
```
{"code":"DONE","msg":"","data":[{"code":"OK","msg":"","data":null},{"code":"OK","msg":"","data":null}]}
```
