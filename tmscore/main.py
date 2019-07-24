import DataController
import Distributer
from RouteFinder import RouteFinder

def main():
    DataController.loadDataFromCache()
    #DataController.loadData('data.txt')
    Distributer.clustering()
    DataController.storeTSPFile('data')

    finder = RouteFinder()
    for fname in DataController.getTSPFilenames():
        finder.route(fname)

if __name__ == "__main__":
    main()