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

      this.apiService.loadDataBase().subscribe({
        error: (err) => {
          alert(err.toString());
        },
        complete: () => {
        },
        next: () => {
        }
      });
    }
  }
}
