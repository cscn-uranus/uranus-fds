import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ManageRoutingModule} from "./manage-routing.module";


import {ManageComponent} from "./manage.component";
import {DsmImportComponent} from "./dsm/dsm-import.component";
import {EndpointComponent} from "./endpoint/endpoint.component";
import {DsmImportService} from "./dsm/dsm-import.service";
import {HttpClientModule} from "@angular/common/http";
import {NgZorroAntdModule} from "ng-zorro-antd";
import {FieJobComponent} from "./job/fie-job.component";
import {DsmListComponent} from "./dsm/dsm-list.component";
import {FieJobService} from "./job/fie-job.service";


@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,

    NgZorroAntdModule.forRoot(),


    HttpClientModule,

    ManageRoutingModule,

  ],
  exports: [],
  declarations: [
    ManageComponent,
    FieJobComponent,
    EndpointComponent,
    DsmListComponent,
    DsmImportComponent,
  ],
  providers: [
    DsmImportService,
    FieJobService,
  ],
})
export class ManageModule {
}
