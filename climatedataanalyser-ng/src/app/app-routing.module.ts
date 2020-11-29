import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AnalyticsComponent} from "./analytics/analytics.component";
import {DatabaseComponent} from "./database/database.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {ClimatesComponent} from "./climates/climates.component";


const routes: Routes = [
  {
    path:'analytics',
    component:AnalyticsComponent

  },
  {
    path:'database',
    component:DatabaseComponent

  },
  { path:'climates',
    component:ClimatesComponent

  },
  {
    path:'',
    component:AnalyticsComponent,
    pathMatch:'full'

  },
  {
    path:'**',
    component:NotFoundComponent,

  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
