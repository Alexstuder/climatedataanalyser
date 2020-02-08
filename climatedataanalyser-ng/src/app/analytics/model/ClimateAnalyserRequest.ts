import {GpsPoint} from './GpsPoint';

export class ClimateAnalyserRequest {

  public bundesland: string;
  public gps1: GpsPoint;
  public gps2: GpsPoint;
  public yearOrigine: string;
  public yearToCompare: string;

  constructor(
    bundesland = '',
    gps1 = new GpsPoint(),
    gps2 = new GpsPoint(),
    yearOrigine = '2000',
    yearToCompare = '2017'
    ) {
  }
}

