# Welcome to the REST API for Blue Bat.

command to run - mvn clean package && cd travel-agency-web && mvn cargo:run


HEADERS ARE -
-H "Content-Type: application/json"
-H "Accept: application/json"


#Get show-list of trips
GET - localhost:8080/pa165/rest/trips

TEST - curl --location --request GET 'localhost:8080/pa165/rest/trips' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'





#GET the trip  
GET - localhost:8080/pa165/rest/trips/{id}
id- this should be an id of the trip in database

curl --location --request GET 'localhost:8080/pa165/rest/trips/1' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'



#Create the trip  
POST - localhost:8080/pa165/rest/trips/create

BODY-
{"name":string,"dateFrom": Date,"dateTo":Date,"destination":string,"availableTrips": positive number,"prices":Array<Price>}


TEST - curl --location --request POST 'localhost:8080/pa165/rest/trips/create' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Yucatan",
"dateFrom": "2022-05-01",
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



#Delete the trip  
DELETE - localhost:8080/pa165/rest/trips/{id}
id- this should be an id of the trip in database

TEST - curl --location --request DELETE 'localhost:8080/pa165/rest/trips/1' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'





#Update the trip  
PUT - localhost:8080/pa165/rest/trips
BODY-{"id": number, "name":string,"dateFrom": Date,"dateTo":Date,"destination":string,"availableTrips": positive number,"prices":[]}

TEST - curl --location --request PUT 'localhost:8080/pa165/rest/trips' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{   "id":2,
"name": "Yucatan",
"dateFrom": "2023-05-01",
"dateTo": "2022-05-15",
"destination": "Ca",
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




#Create the reservation  
POST - localhost:8080/pa165/rest/reservation/create
BODY-{"customerId":number,"tripId":number, "excursionIds":Array<number>}

TEST- curl --location --request POST 'localhost:8080/pa165/rest/reservations/create' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{"customerId":1,"tripId":1, "excursionIds":[]}'



#Get the reservation  
GET - localhost:8080/pa165/rest/trips/{id}

TEST - curl --location --request GET 'localhost:8080/pa165/rest/reservations/1' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'