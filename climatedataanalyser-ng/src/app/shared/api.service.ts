import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import{Bundeslaender} from "../analytics/model/bundeslaender";
import {ViewModelAnalytics} from "../analytics/model/view-model-analytics";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL= window["cfgApiBaseUrl"] + "/api" ;
  private LOAD_DATABASE_URL=`${this.BASE_URL}\\database\\batchImportStart\\`;
  private ANALYTICS_INIT_URL=`${this.BASE_URL}\\analytics\\`;
  private ANALYTICS_BY_BUNDESLAND_URL=`${this.BASE_URL}\\analytics\\byBundesland\\`;

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string>{
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }
  initAnalytics(): Observable<Bundeslaender>{
    return this.http.get<Bundeslaender>(this.ANALYTICS_INIT_URL);
  }

  getAnalyticsByBundesland(bundesland:string): Observable<ViewModelAnalytics>{
    return this.http.get<ViewModelAnalytics>(this.ANALYTICS_BY_BUNDESLAND_URL + bundesland);
  }



}
