import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import{Bundeslaender} from "../analytics/model/bundeslaender";
import {ViewModelAnalytics} from "../analytics/model/view-model-analytics";
import {ClimateAnalyserRequestDto} from "../analytics/model/ClimateAnalyserRequestDto";
import {GpsPoint} from "../analytics/model/GpsPoint";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL= window["cfgApiBaseUrl"] + "/api" ;
  private LOAD_DATABASE_URL=`${this.BASE_URL}\\database\\batchImportStart\\`;
  private ANALYTICS_INIT_URL=`${this.BASE_URL}\\analytics\\`;
  private ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL=`${this.BASE_URL}\\analytics\\request\\`;
  private ANALYTICS_BY_=`${this.BASE_URL}\\analytics\\byBundesland\\`;

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string>{
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }
  initAnalytics(): Observable<Bundeslaender>{
    return this.http.get<Bundeslaender>(this.ANALYTICS_INIT_URL);
  }

  //getAnalyticsByBundesland(bundesland:string): Observable<ViewModelAnalytics>{
  //  return this.http.get<ViewModelAnalytics>(this.ANALYTICS_BY_BUNDESLAND_URL + bundesland);
  //}

  getAnalyticsByRequest(bundesland:string,gps1:GpsPoint,gps2:GpsPoint,yearOrigine:string,yearToCompare:string): Observable<HttpEvent<ClimateAnalyserRequestDto>>{
    const req = new HttpRequest('POST', this.ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL, {bundesland,gps1,gps2,yearOrigine,yearToCompare}, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }




}
