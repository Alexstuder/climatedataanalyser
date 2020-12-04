import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Bundeslaender} from '../analytics/model/bundeslaender';
import {GpsPoint} from '../analytics/model/GpsPoint';
import {ClimateAnalyserResponseDto} from '../analytics/model/ClimateAnalyserResponseDto';
import {ClimateAnalyserRequest} from '../analytics/model/ClimateAnalyserRequest';
import {DbLoadResponseDto} from '../database/model/DbLoadResponseDto';
import {ClimateResponseDto} from "../climates/model/ClimateResponseDto";


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  // private BASE_URL = window.cfgApiBaseUrl + '/api' ;
  //private BASE_URL = window["cfgApiBaseUrl"] + "/api";
  private BASE_URL = window["cfgApiBaseUrl"] + "/api";
  private LOAD_DATABASE_URL = `${this.BASE_URL}\\database\\batchImportStart\\`;
  private LOAD_DATABASE_DATA_URL = `${this.BASE_URL}\\database\\`;
  private ANALYTICS_INIT_URL = `${this.BASE_URL}\\analytics\\`;
  private ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL = `${this.BASE_URL}\\analytics\\request\\`;
  private CLIMATE_RECORDS = `${this.BASE_URL}\\climateRecords\\`;
  private climateRequestDto: ClimateAnalyserRequest;

  private JSON:string = 'json';

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string> {
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }

  initAnalytics(): Observable<Array<String>> {
    return this.http.get<Array<String>>(this.ANALYTICS_INIT_URL);
  }

  initDbLoad(): Observable<HttpEvent<DbLoadResponseDto>> {
    const req = new HttpRequest('GET', this.LOAD_DATABASE_DATA_URL, {}, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request<DbLoadResponseDto>(req);
  }

  getColumns(): string[] {
    return ['stepName', 'startTime', 'stepEndTime', 'stepStatus', 'readCount', 'writeCount']; }

    // tslint:disable-next-line:max-line-length
  getAnalyticsByRequest(bundesland: string, gps1: GpsPoint, gps2: GpsPoint, yearOrigine: string, yearToCompare: string): Observable<HttpEvent<ClimateAnalyserResponseDto>> {
    // tslint:disable-next-line:max-line-length
    const req = new HttpRequest('POST', this.ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL, {bundesland, gps1, gps2, yearOrigine, yearToCompare}, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request<ClimateAnalyserResponseDto>(req);
  }
  getAnalyticsByRequest2(climateAnalyserRequestDto: ClimateAnalyserRequest): Observable<HttpEvent<ClimateAnalyserResponseDto>> {
    const req = new HttpRequest('POST', this.ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL, {climateAnalyserRequestDto}, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request<ClimateAnalyserResponseDto>(req);
  }
  //
/*  public getClimateRecords(bundesland: string
                           , gps1Lat: string
                           , gps1Long: string
                           , gps2Lat: string
                           , gps2Long: string
                           , startYear: string
                           , distanceYear: string
  ){
    const httpParms = new HttpParams().set('bundesland', bundesland)
                                      .set('gps1.lat', gps1Lat)
                                      .set('gps1.long', gps1Long)
                                      .set('gps2.lat', gps2Lat)
                                      .set('gps2.long', gps2Long)
                                      .set('startYear', startYear)
                                      .set('distanceYear', distanceYear);
    return this.http.get<ClimateResponseDto>(this.CLIMATE_RECORDS,{params:  httpParms});

  }*/
  public getClimateRecords(bundesland: string
    , gps1Lat: string
    , gps1Long: string
    , gps2Lat: string
    , gps2Long: string
    , startYear: string
    , distanceYear: string
  ): Observable<HttpEvent<ClimateResponseDto>>{
    const httpParms = new HttpParams().set('bundesland', bundesland)
      .set('gps1.lat', gps1Lat)
      .set('gps1.long', gps1Long)
      .set('gps2.lat', gps2Lat)
      .set('gps2.long', gps2Long)
      .set('startYear', startYear)
      .set('distanceYear', distanceYear);
    const req = new HttpRequest('GET',this.CLIMATE_RECORDS, {} ,{
      reportProgress:true,
      responseType: 'json',
      params: httpParms
    });
    return this.http.request<ClimateResponseDto>(req);

  }
}
