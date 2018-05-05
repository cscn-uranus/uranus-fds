import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EndpointComponent} from "./endpoint/endpoint.component";
import {DataimportComponent} from "./dataimport/dataimport.component";
import {ManageComponent} from "./manage.component";
import {JobComponent} from "./job/job.component";

const appManageRoutes: Routes = [
  {
    path: ManageComponent.url, component: ManageComponent,
    data: {
      title: ManageComponent.title,
      description: ManageComponent.description,
    },
    children: [
      {
        path: 'job', component: JobComponent,
        data:
          {
            title: '作业',
            description: '作业的管理',
          }
      },
      {
        path: DataimportComponent.path, component: DataimportComponent,
        data: {
          title: DataimportComponent.title,
          description: DataimportComponent.description,
        }
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
