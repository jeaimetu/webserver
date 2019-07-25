import DataBase as db
from sklearn.cluster import KMeans
import json
from pymongo import MongoClient

uri = 'mongodb://heroku_9q71xw0m:ipioujieemc6m1ejb7gac2g2ol@ds255107.mlab.com:55107/heroku_9q71xw0m'

def clustering():
    client = MongoClient(uri)
    db = client.get_default_database()
    songs = db['tmssample2']
    
    kmeans = KMeans(n_clusters=db.num_cluster)
    kmeans.fit_predict(db.df)
    for i in range(db.num_cluster):
        db.dict_Cluster[i] = []

    i = 0
    for idx, row in db.df.iterrows():
        cluster_idx = kmeans.labels_[i]
        id = idx
        db.dict_Cluster[cluster_idx].append(db.dict_Parcel[id])
        i +=1

    for v in db.dict_Cluster:
        cluster_num = str(v)
        for c in db.dict_Cluster[v]:
            DATA = {'id': c.id, 'lat': c.lat, 'lon': c.lon, 'addr': c.addr}
            songs.insert_one(DATA)
            #print(str(c.id) + ': ' + str(c.lat) + ', ' + str(c.lon) + ', ' + c.addr)
