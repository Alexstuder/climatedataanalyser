import { Component, OnInit } from '@angular/core';
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})
export class DatabaseComponent implements OnInit {

  constructor(private apiService: ApiService) { }

  ngOnInit() {
  }

  loadDataBase() {
    if(confirm("Do you really want to load the Database ? (Takes about 15 min)")){
     this.apiService.loadDataBase().subscribe(
       value => {

         alert("DataBase Successfully loaded !")

       },
       error => {
         alert(error.toString())
       }

     );


    }
  }
}
