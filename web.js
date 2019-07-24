var express = require('express');
var bodyParser = require("body-parser");
var app = express();

//mongo DB
var mongo = require('mongodb');
var ObjectId = require('mongodb').ObjectId;
var MongoClient = require('mongodb').MongoClient;
var url = process.env.MONGODB_URI;


/* serves all the static files 
 app.get(/^(.+)$/, function(req, res){ 
     console.log('static file request : ' + req.params);
	 console.log("app get", req.params[0]);
	 console.log("app get parameter", req.query.name);
     res.sendfile( __dirname + req.params[0]); 
 });
 */

app.get("/getClusters",function(req,res){
	console.log("getClusters is called");
	var doc = { hello: "world" };
	res.status(200).json(doc);
});

 var port = process.env.PORT || 5000;
console.log("port", port);

 app.listen(port, function() {
   console.log("Listening on " + port);
 });

function test(){
	MongoClient.connect(url, function(err, db) {
		var dbo = db.db("heroku_9q71xw0m");
		var myobj = { boardId : "1",  account : "11111" , date : "22222" };
		dbo.collection("tmsdb").insertOne(myobj, function(err, insres){
			if (err) throw err;
		});
	});	
}

//test();
