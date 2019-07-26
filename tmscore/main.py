import DataController
import Distributer
from RouteFinder import RouteFinder
import sys
import Adapter as adap

def main():
    if sys.argv[1] == "setClusters":
        adap.setClusters()
    elif sys.argv[1] == "getClusters":
        adap.getClusters()
    # elif sys.argv[1] == "setRoute":
    #    adap.setRoute()

if __name__ == "__main__":
    main()