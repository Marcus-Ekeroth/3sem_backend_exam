2.3
The task doesn't specify if guide should have an id but i see that it uses an id in 3.3.1 and 2.4.4,
so i am adding an id to it.

3.3.2

getAll:

[
{
"id": 1,
"startTime": [
9,
0
],
"endTime": [
12,
0
],
"startPosition": "Start Point A",
"name": "Trip A",
"price": 100.0,
"category": "BEACH"
},
{
"id": 2,
"startTime": [
14,
0
],
"endTime": [
17,
0
],
"startPosition": "Start Point B",
"name": "Trip B",
"price": 150.0,
"category": "SEA"
},
{
"id": 3,
"startTime": [
10,
0
],
"endTime": [
13,
0
],
"startPosition": "Start Point C",
"name": "Trip C",
"price": 120.0,
"category": "FOREST"
},
{
"id": 4,
"startTime": [
15,
0
],
"endTime": [
18,
0
],
"startPosition": "Start Point D",
"name": "Trip D",
"price": 180.0,
"category": "SNOW"
}
]

getbyid:

{
"id": 1,
"startTime": [
9,
0
],
"endTime": [
12,
0
],
"startPosition": "Start Point A",
"name": "Trip A",
"price": 100.0,
"category": "BEACH"
}

create:

{
"id": 1,
"startTime": [
8,
0
],
"endTime": [
17,
0
],
"startPosition": "New York",
"name": "New York City Tour",
"price": 150.0,
"category": "FOREST"
}

update:

{
"id": 1,
"startTime": [
8,
0
],
"endTime": [
17,
0
],
"startPosition": "New York",
"name": "New York City Tour",
"price": 150.0,
"category": "FOREST"
}

delete:

addguidetotrip:
{
"id": 1,
"startTime": [
9,
0
],
"endTime": [
12,
0
],
"startPosition": "Start Point A",
"name": "Trip A",
"price": 100.0,
"category": "BEACH"
}

populate:

{
"status": 200,
"message": "Database populated"
}

3.3.5
It makes sense to use put instead of post, as we are updating an existing trip by adding
it to a guide's list of trips. Post would have made more sense to use if we were creating new
data and not altering existing data.

4.1
Response body from getbyid when using an id that does not correspond to a trip in the database:
{
"status": 404,
"message": "Trip not found Cannot invoke \"dat.entities.Trip.getId()\" because \"trip\" is null"
}