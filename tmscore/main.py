import DataController
import Distributer
from RouteFinder import RouteFinder
import sys
import Adapter as adap

def main():
    if sys.argv[1] == "setClusters":
        adap.setClusters()
    elif sys.argv[1] == "getClusters":
        req_date = sys.argv[2]
        adap.getClusters(req_date)
    # elif sys.argv[1] == "setRoute":
    #    adap.setRoute()

if __name__ == "__main__":
    main()
