
import pandas as pd

num_cluster = 0

list_ParcelRaw = []
dict_Parcel = {}
dict_Cluster = {}

df = pd.DataFrame(columns=('lon', 'lat'))
tspFiles = []

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