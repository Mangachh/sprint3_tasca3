const MongoClient = require('mongodb').MongoClient;
const url = "mongodb://localhost:27017/";

// usamos esto para no repetir
const n_database = "floristeria";
const n_stocks = "stocks";
const n_tickets = "tickets";
const n_tenda = "tenda";

MongoClient.connect(url, { useNewUrlParser: true, useUnifiedTopology: true }, function (err, db) {
    if (err) throw err;
    var dbo = db.db(n_database);
    console.log("Database Created");

    // creamos las colecciones, 
    createCollection(n_stocks, dbo);
    createCollection(n_tickets, dbo);
	
	var docs = [{"nom": "Flor_Mongo"}]
	
	dbo.collection(n_tenda).insertMany(docs);
	
	docs = [
			{"nom": "flor_1", "tipus": "Flor", "amount": 7, "price": 2.45, "color": "vermell" },
			{"nom": "flor_2", "tipus": "Flor", "amount": 4, "price": 1.66, "color": "blau" },
			{"nom": "dec_1", "tipus": "Decoracio", "amount": 12, "price": 4.85, "material": "Plastic"},
			{"nom": "dec_2", "tipus": "Decoracio", "amount": 1, "price": 2.45, "material": "Fusta"},
			{"nom": "arbre_1", "tipus": "Arbre", "amount": 6, "price": 2.65, "alcada": 3.24},
			{"nom": "arbre_2", "tipus": "Arbre", "amount": 2, "price": 4.85,"alcada": 0.54}	
		] 
		
	dbo.collection(n_stocks).insertMany(docs);
	
	docs = [
			{"id": 1, "items": [{"nom": "flor_40", "tipus": "Flor", "amount": 7, "price": 2.45, "color": "vermell"},
								{"nom": "flor_12", "tipus": "Flor", "amount": 4, "price": 1.66, "color": "blau"}]},
			{"id": 2, "items": [{"nom": "dec_1", "tipus": "Decoracio", "amount": 12, "price": 4.85, "material": "Plastic"}]},
			{"id": 3, "items": [{"nom": "dec_24", "tipus": "Decoracio", "amount": 1, "price": 2.45, "material": "Fusta"},
								{"nom": "arbre_111", "tipus": "Arbre",  "amount": 6, "price": 2.65, "alcada": 3.24},
								{"nom": "arbre_234", "tipus": "Arbre", "amount": 2,	"price": 4.85,"alcada": 0.54}]}			
			]
	
	dbo.collection(n_tickets).insertMany(docs);
};