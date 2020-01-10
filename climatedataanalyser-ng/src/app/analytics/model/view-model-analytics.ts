import {ClimateAnalyserOneTemp} from "./climate-analyser-one-temp";

export interface ViewModelAnalytics {

  year:string;
  bundesland:string;
  original:ClimateAnalyserOneTemp;
  compare:ClimateAnalyserOneTemp;


}
