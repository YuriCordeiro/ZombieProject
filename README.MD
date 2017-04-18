# ZombieProject (ZP)

## Documentation

#### Create Gender
URL: **/genders**

Method: **POST**

Example:
```json
{
	"genderDescription": "Male"
}
```

---

#### Update Gender
URL: **/genders**

Method: **PUT**

Example:
```json
{
	"genderDescription": "Female"
}
```

---

#### List Genders
URL: **/genders**

Method: **GET**

---

#### Show Gender
URL: **/genders/:genderId**

Method: **GET**

---


#### Remove Gender
URL: **/genders/:genderId**

Method: **DELETE**

---


#### Create Inventory
URL: **/inventories**

Method: **POST**

---

#### Show Inventory
URL: **/inventories/:id**

Method: **GET**

---

#### List Inventory
URL: **/inventories/**

Method: **GET**

---

#### Delete Inventory
URL: **/inventories/:id**

Method: **DELETE**

---

#### Update Inventory
URL: **/inventories/:id**

Method: **PUT**

Example:
```json
{
	"items": [{"itemDescription": "Water", "itemAmount": 3, "itemPrice": 4}]
}
```

---

#### Create Item
URL: **/items**

Method: **POST**

Example:
```json
{
	"itemDescription": "Water",

	"itemAmount": 3,

	"itemPrice": 4,
	
	"inventory": {"inventoryCode": 1}
}
```

---

#### Update Item
URL: **/items/:itemId**

Method: **PUT**

Example:
```json
{
	"itemDescription": "Food",

	"itemAmount": 1,

	"itemPrice": 3,
	
	"inventory": {"inventoryCode": 1}
}
```

---

#### List Items
URL: **/items**

Method: **GET**

---

#### Show Item
URL: **/items/:itemId**

Method: **GET**

---

#### Delete Item
URL: **/items/:itemId**

Method: **DELETE**

---

#### Create Local
URL: **/locals**

Method: **POST**

Example:
```json
{
	"latitude": "130.120", "longitude": "-21.217"
}
```

---

#### Update Local
URL: **/locals/:idLocal**

Method: **PUT**

Example:
```json
{
	"latitude": "-299.999", "longitude": "001.991"
}
```

---

#### List Local
URL: **/locals**

Method: **GET**

---

#### Show Local
URL: **/locals/:idLocal**

Method: **GET**

---


#### Remove Local
URL: **/locals/:idLocal**

Method: **DELETE**

---

#### Create Survivor
URL: **/survivors**

Method: **POST**

Example:
```json
{
	"survivorName": "John Travolta",
	
	"survivorAge" : 63,

	"local" : {"latitude": "21.007", "longitude": "-10.370"} ,
 
	"survivorGender":  {"genderId": 1},

	"infected": false,
	
	"amountOfInfectedWarnings" : 0,
	
	"points": 250
}
```

---

#### Update Survivor
URL: **/survivors/:id**

Method: **PUT**

Example:
```json
{
	"survivorName": "Sophie",
	
	"survivorAge" : 17,

	"local" : {"latitude": "-30.005", "longitude": "-71.010"},
 
	"survivorGender":  {"genderId": 2},

	"infected": false,
	
	"amountOfInfectedWarnings" : 1,
	
	"points": 100
}
```

---

#### List Survivors
URL: **/survivors**

Method: **GET**

---

#### Delete Survivor
URL: **/survivors/:id**

Method: **DELETE**

---

#### Flag Survivor As Infected
URL: **/survivors/warnasinfected/:survivorId**

Method: **PUT**

---

#### Trade Survivor's Items
URL: **/survivors/trader/:tradingSurvivorCode/item/:itemCode/amount/:amountOfItemToTrade/benefited/:survivorReceptorCode**

Method: **PUT**

---

#### Update Survivor's Location
URL: **/survivors/:survivorId/update_location/:latitude/:longitude**

Method: **PUT**

---

### Percentage of Non Infected Survivors
URL: **/reports/percentages/non_infected**

Method: **GET**

---

### Percentage of Infected Survivors
URL: **/reports/percentages/infected**

Method: **GET**

---

### Disclaimer
Both the trading and the percentage of recourses per survivor report were not implemented.
The trading is implemented (logical) but doing no update on database.

### How to Start Application
Run the SpringBootApp.java as JavaApplication to start playing. Then go to Postman and enjoy. (:
