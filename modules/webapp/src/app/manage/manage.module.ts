import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ManageRoutingModule} from "./manage-routing.module";


import {ManageComponent} from "./manage.component";
import {DataimportComponent} from "./dataimport/dataimport.component";
import {AdapterComponent} from "./adapter/adapter.component";
import {DataimportService} from "./dataimport/dataimport.service";
import {HttpClientModule} from "@angular/common/http";
import {NgZorroAntdModule} from "ng-zorro-antd";


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
    AdapterComponent,
    DataimportComponent,
  ],
  providers: [
    DataimportService,
  ],
})
export class ManageModule {
}
