import pandas as pd
import DataBase as db
from json import JSONEncoder
from pymongo import MongoClient

uri = 'mongodb://heroku_9q71xw0m:ipioujieemc6m1ejb7gac2g2ol@ds255107.mlab.com:55107/heroku_9q71xw0m'
client = MongoClient(uri)
mongodb = client.get_default_database()

num_cluster = 0

list_ParcelRaw = []
dict_Parcel = {}
dict_Cluster = {}

df = pd.DataFrame(columns=('lon', 'lat'))
tspFiles = []

def dropDB(name):
    mongodb.drop_collection(name)

def getTMSDB(name):
    return mongodb[name]

class ParcelEncoder(JSONEncoder):
    def default(self, o):
        return o.__dict__

class ParcelRaw:
    def __init__(self, id, addr):
        self.id = id
        self.addr = addr

class Parcel:
    def __init__(self, id, addr, lat, lon):
        self.id = id
        self.addr = addr
        self.lat = lat
        self.lon = lon