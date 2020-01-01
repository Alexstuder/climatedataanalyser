import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL= window["cfgApiBaseUrl"] + "/api" ;
  private LOAD_DATABASE_URL=`${this.BASE_URL}\\database\\batchImportStart\\`;

  constructor(private http: HttpClient) { }

  loadDataBase(): Observable<string>{
    return this.http.get<string>(this.LOAD_DATABASE_URL);
  }



}
