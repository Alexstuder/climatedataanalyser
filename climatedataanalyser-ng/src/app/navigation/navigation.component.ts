import {Component, OnInit} from '@angular/core';
import {HttpEventType} from '@angular/common/http';
import {ApiService} from '../shared/api.service';
import {AppInfoDto} from './model/AppInfoDto';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  appinfo: AppInfoDto;

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {

    this.apiService.appInfo().subscribe({
      next: (value) => {
        switch (value.type) {
          case HttpEventType.Response:
            this.appinfo = value.body;
        }
      },
      error: () => {
        alert('An error occurred ,while getting AppInfo from Backend!');
      }
    });
  }
}
