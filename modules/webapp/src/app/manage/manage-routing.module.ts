import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdapterComponent} from "./adapter/adapter.component";
import {DataimportComponent} from "./dataimport/dataimport.component";
import {ManageComponent} from "./manage.component";

const appManageRoutes: Routes = [
  {
    path: ManageComponent.url, component: ManageComponent,
    data: {
      title: ManageComponent.title,
      description: ManageComponent.description,
    },
    children: [
      {
        path: DataimportComponent.path, component: DataimportComponent,
        data: {
          title: DataimportComponent.title,
          description: DataimportComponent.description,
        }
      },
      {
        path: 'adapter', component: AdapterComponent,
        data:
          {
            title: '适配器管理',
            description: '外部适配器的管理',
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
