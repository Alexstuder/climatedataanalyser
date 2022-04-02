import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {GpsPoint} from '../analytics/model/GpsPoint';
import {ClimateAnalyserResponseDto} from '../analytics/model/ClimateAnalyserResponseDto';
import {DbLoadResponseDto} from '../database/model/DbLoadResponseDto';
import {ClimateResponseDto} from '../climates/model/ClimateResponseDto';
import {environment} from '../../environments/environment';
import {AppInfoDto} from '../navigation/model/AppInfoDto';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL = environment.BASE_URL + '/api';
  private LOAD_DATABASE_URL = `${this.BASE_URL}\\database\\batchImportStart\\`;
  private LOAD_DATABASE_DATA_URL = `${this.BASE_URL}\\database\\`;
  private APPINFO_URL = `${this.BASE_URL}\\appInfo\\`;
  private ANALYTICS_INIT_URL = `${this.BASE_URL}\\analytics\\`;
  private ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL = `${this.BASE_URL}\\analytics\\request\\`;
  private CLIMATE_RECORDS = `${this.BASE_URL}\\climateRecords\\`;
  public dbIsLoaded = false;

  constructor(private http: HttpClient) {
  }

  loadDataBase(withFTP: string): Observable<string> {
    let myQueryparams = new HttpParams();
    myQueryparams = myQueryparams.append('withFTP', withFTP);
    return this.http.get<string>(this.LOAD_DATABASE_URL, {params: myQueryparams});
  }

  appInfo(): Observable<HttpEvent<AppInfoDto>> {
    const req = new HttpRequest('GET', this.APPINFO_URL, {}, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request<AppInfoDto>(req);
  }

  initAnalytics(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.ANALYTICS_INIT_URL);
  }

  isDbLoaded(): Observable<HttpEvent<DbLoadResponseDto>> {
    let response: Observable<HttpEvent<DbLoadResponseDto>>;
    const req = new HttpRequest('GET', this.LOAD_DATABASE_DATA_URL, {}, {
      reportProgress: true,
      responseType: 'json'
    });
    response = this.http.request<DbLoadResponseDto>(req);
    return response;
  }

  getColumns(): string[] {
    return ['stepName', 'startTime', 'stepEndTime', 'stepStatus', 'readCount', 'writeCount'];
  }


  getAnalyticsByRequest(bundesland: string,
                        gps1: GpsPoint,
                        gps2: GpsPoint,
                        yearOrigine: string,
                        yearToCompare: string): Observable<HttpEvent<ClimateAnalyserResponseDto>> {


    const req = new HttpRequest('POST',
      this.ANALYTICS_BY_CLIMATE_ANALYSER_REQUEST_DTO_URL,
      {bundesland, gps1, gps2, yearOrigine, yearToCompare},
      {
        reportProgress: true,
        responseType: 'json'
      }
    );
    return this.http.request<ClimateAnalyserResponseDto>(req);
  }

  public getClimateRecords(bundesland: string,
                           gps1Lat: string,
                           gps1Long: string,
                           gps2Lat: string,
                           gps2Long: string,
                           startYear: string,
                           distanceYear: string,
  ): Observable<HttpEvent<ClimateResponseDto>> {
    const httpParms = new HttpParams().set('bundesland', bundesland)
      .set('gps1.lat', gps1Lat)
      .set('gps1.long', gps1Long)
      .set('gps2.lat', gps2Lat)
      .set('gps2.long', gps2Long)
      .set('startYear', startYear)
      .set('distanceYear', distanceYear);
    const req = new HttpRequest('GET', this.CLIMATE_RECORDS, {}, {
      reportProgress: true,
      responseType: 'json',
      params: httpParms
    });
    return this.http.request<ClimateResponseDto>(req);

  }
}
