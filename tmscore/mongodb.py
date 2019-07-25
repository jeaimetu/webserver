import sys
import os
import pymongo
from pymongo import MongoClient
SEED_DATA = [
    {
        'decade': '1970s',
        'artist': 'Debby Boone',
        'song': 'You Light Up My Life',
        'weeksAtOne': 10
    },
    {
        'decade': '1980s',
        'artist': 'Olivia Newton-John',
        'song': 'Physical',
        'weeksAtOne': 10
    },
    {
        'decade': '1990s',
        'artist': 'Mariah Carey',
        'song': 'One Sweet Day',
        'weeksAtOne': 16
    }
]

uri = 'mongodb://heroku_9q71xw0m:ipioujieemc6m1ejb7gac2g2ol@ds255107.mlab.com:55107/heroku_9q71xw0m'

def main(args):
    client = MongoClient(uri)
    #db = client['prod-db']
    #client = pymongo.MongoClient(uri)
    db = client.get_default_database()
    
    # First we'll add a few songs. Nothing is required to create the songs 
    # collection; it is created automatically when we insert.

    songs = db['tmssample']

    # Note that the insert method can take either an array or a single dict.
    #songs.insert_many(SEED_DATA)

    # Then we need to give Boyz II Men credit for their contribution to
    # the hit "One Sweet Day".
    query = {'song': 'One Sweet Day'}
    songs.update(query, {'$set': {'artist': 'Mariah Carey ft. Boyz II Men'}})

    # Finally we run a query which returns all the hits that spent 10 or
    # more weeks at number 1.

    #cursor = songs.find({'weeksAtOne': {'$gte': 10}}).sort('decade', 1)

    #for doc in cursor:
        #print ('In the %s, %s by %s topped the charts for %d straight weeks.' %
               #(doc['decade'], doc['song'], doc['artist'], doc['weeksAtOne']))
    
    ### Since this is an example, we'll clean up after ourselves.

    #db.drop_collection('songs')

    ### Only close the connection when your app is terminating

    client.close()


if __name__ == '__main__':
    main(sys.argv[1:])
