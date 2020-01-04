import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import{Bundeslaender} from "../analytics/model/bundeslaender";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL= window["cfgApiBaseUrl"] + "/api" ;
  private LOAD_DATABASE_URL=`${this.BASE_URL}\\database\\batchImportStart\\`;
  private ANALYTICS_INIT_URL=`${this.BASE_URL}\\analytics\\`;

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string>{
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }
  initAnalytics(): Observable<Bundeslaender>{
    return this.http.get<Bundeslaender>(this.ANALYTICS_INIT_URL);
  }




}
