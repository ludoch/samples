import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Jhipsterjava11SharedModule } from 'app/shared/shared.module';
import { UserMgmtComponent } from './user-management.component';
import { UserMgmtDetailComponent } from './user-management-detail.component';
import { UserMgmtUpdateComponent } from './user-management-update.component';
import { UserMgmtDeleteDialogComponent } from './user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';

@NgModule({
  imports: [Jhipsterjava11SharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [UserMgmtComponent, UserMgmtDetailComponent, UserMgmtUpdateComponent, UserMgmtDeleteDialogComponent],
  entryComponents: [UserMgmtDeleteDialogComponent]
})
export class UserManagementModule {}
