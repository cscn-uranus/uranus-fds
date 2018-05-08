import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EndpointComponent} from "./endpoint/endpoint.component";
import {ManageComponent} from "./manage.component";
import {FieJobComponent} from "./job/fie-job.component";
import {DsmListComponent} from "./dsm/dsm-list.component";
import {DsmImportComponent} from "./dsm/dsm-import.component";

const appManageRoutes: Routes = [
  {
    path: ManageComponent.url, component: ManageComponent,
    data: {
      title: ManageComponent.title,
      description: ManageComponent.description,
    },
    children: [
      {
        path: 'job', component: FieJobComponent,
        data:
          {
            title: '作业',
            description: '作业的管理',
          }
      },
      {
        path: DsmListComponent.path, component: DsmListComponent,
        data: {
          title: DsmListComponent.title,
          description: DsmListComponent.description,
        },
        children: [
          {
            path: DsmImportComponent.path, component: DsmImportComponent,
            data: {
              title: DsmImportComponent.title,
              description: DsmImportComponent.description,
            }
          },
        ]
      },
      {
        path: 'endpoint', component: EndpointComponent,
        data:
          {
            title: '终结点',
            description: '终结点的管理',
          }
      }
    ]
  },

];

@NgModule({
  imports: [
    RouterModule.forChild(appManageRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class ManageRoutingModule {
}
