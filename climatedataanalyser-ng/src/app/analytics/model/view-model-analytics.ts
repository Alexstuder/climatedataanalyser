import {ClimateAnalyserOneTemp} from "./climate-analyser-one-temp";

export interface ViewModelAnalytics {

  year:string;
  bundesland:string;
  mostClimateAnalyserData:ClimateAnalyserOneTemp;
  climateAnalyserDataByStationId:ClimateAnalyserOneTemp;


}
