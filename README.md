# drones-management
This project has Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access

# How to run the project
* Running the dockerfile

### Note:  dockerfile has two stages; 
1. mvn clean package to clean and build the project into jar (which might takes couple of minutes the first run as it downloads the original images)
2. To run the jar.
```
docker build -t drones-management-1.0.0 .
```
```
docker run -p 8080:8090 -d drones-management-1.0.0:latest
```

# APIs

## Drone CRUD APIs
* Get All Drones
```
curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone
```
* Register a Drone
```
curl --request PUT \
  --url http://localhost:8080/api/v1/drone-management/drone \
  --header 'Content-Type: application/json' \
  --data '{
	"serialNumber": "aed416a8-ac69-4d51-834d-52a90dc26b2a",
		"model": "LIGHT_WEIGHT",
		"weightLimit": 250,
		"batteryCapacity": 24,
		"state": "IDLE"
}'
```
* Update Drone (partially update any field of the drone)
```
curl --request PATCH \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber} \
  --header 'Content-Type: application/json' \
  --data '{
	"state": "LOADING"
}'
```
* Delete a drone
```
curl --request DELETE \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber}
```
* Get Available Drones
```
curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone/available
  ```
 * Get Drone by Serial Number
 ```
 curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber}
 ```
 * Get Drone Battery Capacity
 ```
 curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber}/battery-capacity
 ```
 * Get Drone Loaded Medications
 ```
 curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber}/medication
```
* Load Drone with Medications
```
curl --request POST \
  --url http://localhost:8080/api/v1/drone-management/drone/{serialNumber}/load \
  --header 'Content-Type: application/json' \
  --data '{
	"medicationCodeList":["DDDD_12345","AAAA_12345"]
}'
```
* Get Drone Battery/State History
```
curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/drone/history
```

## Medication CRUD APIs
* Get All Medications
```
curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/medication
```

* Get Medication by Code
```
curl --request GET \
  --url http://localhost:8080/api/v1/drone-management/medication/{code}
```

* Register Medication
```
curl --request PUT \
  --url http://localhost:8080/api/v1/drone-management/medication \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "panthenol1sad",
	"weight": 100,
	"code":"BBBB_11111"
}'
```
* Update Medication
```
curl --request PATCH \
  --url http://localhost:8080/api/v1/drone-management/medication/{code} \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "kinakomb",
	"weight": 120,
	"code": "BBBB_11111"
}'
```
* Delete Drone
```
curl --request DELETE \
  --url http://localhost:8080/api/v1/drone-management/medication/{code}
```

## Error Codes
* INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE = 6001
* MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE = 6002
* DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE = 6003
* MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE = 6004
* DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE = 6005
* DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_CODE = 6006
* MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_CODE = 6007
* DRONE_HAS_EXCEEDED_THE_WEIGHT_LIMIT_ERROR_CODE = 6008
* DRONE_BATTER_CAPACITY_IS_BELOW_25_PERCENT_ERROR_CODE = 6009
* DRONE_IS_NOT_LOADED_WITH_MEDICATIONS_ERROR_CODE = 6010
