#!/usr/bin/env python3
import DataBase as db
import DataController
import Distributer
from RouteFinder import RouteFinder
import json

def setClusters(data=None):
    if data is None:
        DataController.loadDataFromCache()
    else:
        DataController.loadData(data)

    Distributer.clustering()
    setRoute()
    print('success setClusters')
    return db.num_cluster

def setRoute(data=None):
    DataController.storeTSPFile('data')
    finder = RouteFinder()
    for fname in DataController.getTSPFilenames():
        finder.route(fname)

def getClusters(date=None):
    print(date)
    DBobj = db.getTMSDB('tmssample')
    cursor = DBobj.distinct('clusterNum', {'date': date})
    
    #for doc in cursor:
        #print(doc)
    jsonStr = json.dumps(db.ParcelEncoder().encode(cursor))
    print(jsonStr)
    #return jsonstr


def getEachCluster(clusterID, date=None):
    #print(date, cluseterID)
    jsonStr = json.dumps(db.ParcelEncoder().encode(db.dict_Cluster[clusterID]))
    return jsonStr

    # - res:
    # sorted List of Parcels
    # {ParcelID, address, lat, lon, deliveryState}
    pass

def getParcelState(parcelID):
    # - res:
    # deliveryState
    # PictureFile
    pass

def setParcelState(parcelID, pictureFile, updateState):
    # - res:
    # deliveryState
    pass
