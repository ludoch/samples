import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jhipsterjava11SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Jhipsterjava11SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class Jhipsterjava11HomeModule {}
