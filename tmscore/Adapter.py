#!/usr/bin/env python3
import json

from django.shortcuts import render
from django.http import HttpResponse
from rq import Queue
from worker import conn

import tmscore.DataBase as db
import tmscore.DataController as dcon
import tmscore.Distributer as distributer
from tmscore.RouteFinder import RouteFinder

q = Queue(connection=conn)


def index(req):
    dcon.loadDataFromFirebaseDB('2019-07-10')
    return HttpResponse('<pre>' + 'Does Firebase activated???' + '</pre>')


def setClusters(req, year, month, day):
    if year == None or month == None or day == None:
        return HttpResponse('<pre>' + 'Invalid URL' + '</pre>')

    result = q.enqueue(setClustersWork, args=(
        year, month, day), job_timeout=600)
    return HttpResponse('<pre>' + 'setClusters() Processing...' + '</pre>')


def setClustersWork(year, month, day, data=None):
    dateForm = '-'.join([str(year), str("%02d" % month), str("%02d" % day)])
    print(dateForm)
    dcon.loadDataFromFirebaseDB(dateForm)
    # if data is None:
    #     dcon.loadDataFromCache()
    # else:
    #     dcon.loadData(data)

    distributer.clustering()
    dcon.saveTSPFile('data')
    finder = RouteFinder()
    for c, fname in enumerate(dcon.getTSPFilenames()):
        finder.route(fname)
        dcon.saveDataToFirebaseDB(dateForm, c, finder.problem, finder.route)
        print('firebaseDB updated for cluster', c)
    print('success setClusters')


def getClusters(req, date=None):
    DBobj = db.getTMSDB('tmssample')
    cursor = DBobj.distinct('clusterNum')  # , {'date': date})

    jsonStr = json.dumps(cursor)
    print(jsonStr)
    return HttpResponse('<pre>' + jsonStr + '</pre>')


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
