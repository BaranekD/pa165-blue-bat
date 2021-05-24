# Welcome to the REST API for Blue Bat.

command to run - mvn clean install && cd travel-agency-web && mvn cargo:run

Accounts
User - nickname:passowrd
Admin- admin:admin

HEADERS ARE -
-H "Content-Type: application/json"
-H "Accept: application/json"

curls work in Ubuntu terminal

#Get show-list of trips
GET - localhost:8080/pa165/rest/trips

TEST - curl -L -X GET 'localhost:8080/pa165/rest/trips'

#GET the trip  
GET - localhost:8080/pa165/rest/trips/{id}
id- this should be an id of the trip in database

TEST - curl -L -X GET 'localhost:8080/pa165/rest/trips/1'



#Create the trip  
POST - localhost:8080/pa165/rest/trips/create

BODY-
{"name":string,"dateFrom": Date,"dateTo":Date,"destination":string,"availableTrips": positive number,"prices":Array<Price>}


TEST -
curl -L -X POST 'localhost:8080/pa165/rest/trips/create' -H 'Accept: application/json' -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW46YWRtaW4=' --data-raw '{
"name": "Yucatan",
"dateFrom": "2023-05-01",
"dateTo": "2022-05-15",
"destination": "Mexico",
"availableTrips": 5,
"prices": [
{
"amount": 5,
"validFrom": "2020-05-17"
},
{
"amount": 100,
"validFrom": "2022-05-17"
},
{
"amount": 83,
"validFrom": "2024-05-17"
}
]
}'

#Update the trip
PUT - localhost:8080/pa165/rest/trips
BODY-{"id": number, "name":string,"dateFrom": Date,"dateTo":Date,"destination":string,"availableTrips": positive number,"prices":[]}

TEST - curl -L -X PUT 'localhost:8080/pa165/rest/trips' -H 'Accept: application/json' -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW46YWRtaW4=' --data-raw '{   "id":2,
"name": "Yucatan",
"dateFrom": "2023-05-01",
"dateTo": "2022-05-15",
"destination": "Mexico",
"availableTrips": 5,
"price":120
}'



#Delete the trip  
DELETE - localhost:8080/pa165/rest/trips/{id}
id- this should be an id of the trip in database

TEST - curl -L -X DELETE 'localhost:8080/pa165/rest/trips/2' -H 'Accept: application/json' -H 'Content-Type: application/json'



#Create the reservation  
POST - localhost:8080/pa165/rest/reservation/create
BODY-{"customerId":number,"tripId":number, "excursionIds":Array<number>}

TEST- curl -L -X POST 'localhost:8080/pa165/rest/reservations/create' -H 'Accept: application/json' -H 'Content-Type: application/json' -H 'Authorization: Basic bmlja25hbWU6cGFzc293cmQ=' --data-raw '{"tripId":1,"excursionIds":[1]}'



#Get the reservation  
GET - localhost:8080/pa165/rest/trips/{id}

TEST - curl -L -X GET 'localhost:8080/pa165/rest/reservations/1' -H 'Accept: application/json' -H 'Content-Type: application/json'