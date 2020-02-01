import {GpsPoint} from "./GpsPoint";

export interface ClimateAnalyserRequestDto {
  // Defines the Bundesland to Compare
  bundesland:string;

  // Defines the GPS Coordinates
  gps1:GpsPoint ;
  gps2:GpsPoint;


  // This Year will be Compared with all StationsID against the StationsId that exists in
  // following parameter : yearToCompare
  yearOrigine:string;
  yearToCompare:string;

}
