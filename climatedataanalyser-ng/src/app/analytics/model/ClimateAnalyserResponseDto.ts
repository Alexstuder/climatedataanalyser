import {ClimateAnalyserOneTemp} from "./climate-analyser-one-temp";
import {GpsPoint} from "./GpsPoint";

export interface ClimateAnalyserResponseDto {

  originYear:string;
  yearToCompare:string;
  bundesland:string;
  gps1:GpsPoint;
  gps2:GpsPoint;
  original:ClimateAnalyserOneTemp;
  compare:ClimateAnalyserOneTemp;
  errorMsg:string;


}
