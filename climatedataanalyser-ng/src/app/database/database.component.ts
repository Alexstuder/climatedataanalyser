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
              // check if DB is Loaded !
              if (this.dbLoadResponseDto.isDbLoaded === 'DB is loaded!') {
                this.apiService.dbIsLoaded = true;
                alert('DB is successfully loaded.');
              }
          }
        },
      }
    );
  }

  loadDataBase() {
    let withFTP = 'false';
    if (confirm('Do you really want to load the Database ? (Takes about 20 min)')) {
      if (confirm('Get fresh files from FTP-Server?')) {
        withFTP = 'true';
      }

      this.apiService.loadDataBase(withFTP).subscribe({
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
