import {GpsPoint} from './GpsPoint';

export class ClimateAnalyserRequest {

  public bundesland: string;
  public gps1: GpsPoint;
  public gps2: GpsPoint;
  public yearOrigine: string;
  public yearToCompare: string;

  constructor(bundesland: string,gps1: GpsPoint,gps2:GpsPoint,yearO:string,yearC:string) {
    this.bundesland = bundesland;
    this.gps1 = gps1;
    this.gps2=gps2;
    this.yearOrigine=yearO;
    this.yearToCompare=yearC;

  }
}

