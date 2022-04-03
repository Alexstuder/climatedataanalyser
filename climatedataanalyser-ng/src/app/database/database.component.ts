import {Component, OnInit} from '@angular/core';
import {ApiService} from '../shared/api.service';
import {HttpEventType} from '@angular/common/http';
import {DbLoadResponseDto} from './model/DbLoadResponseDto';
import {BatchExecutionStatus} from '../shared/dbStatusEnum';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})
export class DatabaseComponent implements OnInit {
  message: string;
  dbLoadResponseDto: DbLoadResponseDto;
  columns: string[];
  currentDbLoadStatus: BatchExecutionStatus;

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {
    console.log('ngOnInit');
    this.getDbStatus();
  }

  async getDbStatus() {
    this.message = '... reading DataBase ...';
    this.currentDbLoadStatus = BatchExecutionStatus.STARTED;
    // do while currentDbLoadStatus==loading
    while (this.currentDbLoadStatus === BatchExecutionStatus.STARTED
    || this.currentDbLoadStatus === BatchExecutionStatus.STARTING) {
      await new Promise(r => setTimeout(r, 2000));
      this.message = '';
      this.apiService.isDbLoaded().subscribe({
          complete: () => {
          }, // completeHandler
          error: () => {
          },    // errorHandler
          next: (value) => {
            if (value.type === HttpEventType.Response) {

              this.currentDbLoadStatus = BatchExecutionStatus[value.body.status];
              this.dbLoadResponseDto = value.body;
              console.log('DTO :', this.dbLoadResponseDto);
              this.columns = this.apiService.getColumns();

              switch (this.currentDbLoadStatus) {
                case BatchExecutionStatus.STARTING: {
                  this.message = 'Database load processing started';
                  break;
                }
                case BatchExecutionStatus.STARTED: {
                  this.message = 'Database is still loading ';
                  break;
                }
                case BatchExecutionStatus.COMPLETED: {
                  console.log('isDbLoaded :', value.body.dbLoadStatus);
                  if (value.body.dbLoadStatus === true) {
                    this.message = 'DB is fully loaded';
                  } else {
                    this.message = 'DB is empty';
                  }
                  break;
                }
                default:
                  this.message = 'Something went Wrong ,DataBase has Status :' + value.body.status;
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
