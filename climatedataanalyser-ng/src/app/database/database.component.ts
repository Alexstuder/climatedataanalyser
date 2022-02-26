import {Component, OnInit} from '@angular/core';
import {ApiService} from '../shared/api.service';
import {HttpEventType} from '@angular/common/http';
import {DbLoadResponseDto} from './model/DbLoadResponseDto';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})
export class DatabaseComponent implements OnInit {

  dbLoadResponseDto: DbLoadResponseDto;
  columns: string[];

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {

    this.columns = this.apiService.getColumns();

    /*    this.apiService.initDbLoad().subscribe(
          value => {

            switch (value.type) {
              case HttpEventType.Response:
                this.dbLoadResponseDto = value.body;

            }
          },
          error => {
            alert('An error occurred ,while getting DB informations from DB-Server');
          }
        );*/


    // ----- new
    this.apiService.initDbLoad().subscribe({
        complete: () => {
        }, // completeHandler
        error: () => {
        },    // errorHandler
        next: (value) => {
          switch (value.type) {
            case HttpEventType.Response:
              this.dbLoadResponseDto = value.body;
          }
        },
      }
    );
  }

  loadDataBase() {
    if (confirm('Do you really want to load the Database ? (Takes about 20 min)')) {
      this.apiService.loadDataBase().subscribe(
        value => {

          alert('DataBase Successfully loaded !');

        },
        error => {
          alert(error.toString());
        }
      );


    }
  }
}
