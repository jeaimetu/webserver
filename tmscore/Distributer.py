import DataBase as db
from sklearn.cluster import KMeans

def clustering():
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
        print("cluster number:" + str(v))
        for c in db.dict_Cluster[v]:
            print(str(c.id) + ': ' + str(c.lat) + ', ' + str(c.lon) + ', ' + c.addr)
