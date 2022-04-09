import {Component, OnInit} from '@angular/core';
import {ApiService} from '../shared/api.service';
import {HttpEventType} from '@angular/common/http';
import {DbLoadResponseDto} from './model/DbLoadResponseDto';
import {DbStatus} from '../shared/dbStatusEnum';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})
export class DatabaseComponent implements OnInit {
  message: string;
  dbLoadResponseDto: DbLoadResponseDto;
  columns: string[];
  currentDbLoadStatus: DbStatus;

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {
    console.log('ngOnInit');
    this.getDbStatus();
  }

  async getDbStatus() {
    this.message = '... reading DataBase ...';
    this.currentDbLoadStatus = DbStatus.loading;
    // do while currentDbLoadStatus==loading
    while (this.currentDbLoadStatus === DbStatus.loading) {
      await new Promise(r => setTimeout(r, 2000));
      this.message = '';
      this.apiService.isDbLoaded().subscribe({
          complete: () => {
          }, // completeHandler
          error: () => {
          },    // errorHandler
          next: (value) => {
            if (value.type === HttpEventType.Response) {

              this.currentDbLoadStatus = DbStatus[value.body.isDbLoaded];
              this.dbLoadResponseDto = value.body;
              this.columns = this.apiService.getColumns();

              switch (this.currentDbLoadStatus) {
                case DbStatus.empty: {
                  this.message = 'DataBase is currently empty';
                  break;
                }
                case DbStatus.failed: {
                  this.message = 'Loading Database has failed';
                  break;
                }
                case DbStatus.loaded: {
                  this.message = '';
                  break;
                }
                case DbStatus.loading: {
                  this.message = 'Database is still loading';
                  break;
                }
                default:
                  console.log('No enum hast matched');
                  break;
              }
            }
          },
        }
      );
    }

  }


  async loadDataBase() {
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
      this.getDbStatus(); // refresh site
    }
  }
}
