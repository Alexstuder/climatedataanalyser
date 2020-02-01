import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import{Bundeslaender} from "../analytics/model/bundeslaender";
import {GpsPoint} from "../analytics/model/GpsPoint";
import {ClimateAnalyserResponseDto} from "../analytics/model/ClimateAnalyserResponseDto";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL= window["cfgApiBaseUrl"] + "/api" ;
  private LOAD_DATABASE_URL=`${this.BASE_URL}\\database\\batchImportStart\\`;
  private ANALYTICS_INIT_URL=`${this.BASE_URL}\\analytics\\`;
  private ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL=`${this.BASE_URL}\\analytics\\request\\`;

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string>{
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }
  initAnalytics(): Observable<Bundeslaender>{
    return this.http.get<Bundeslaender>(this.ANALYTICS_INIT_URL);
  }

  getAnalyticsByRequest(bundesland:string,gps1:GpsPoint,gps2:GpsPoint,yearOrigine:string,yearToCompare:string): Observable<HttpEvent<ClimateAnalyserResponseDto>>{
    const req = new HttpRequest('POST', this.ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL, {bundesland,gps1,gps2,yearOrigine,yearToCompare}, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request<ClimateAnalyserResponseDto>(req);
  }
}
