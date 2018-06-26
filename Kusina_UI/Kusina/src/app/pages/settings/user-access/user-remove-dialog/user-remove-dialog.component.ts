import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { TableComponent } from '../../../../materials/table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { UserAccessService } from '../../../../services/user-access.service';

@Component({
    selector: 'app-user-remove-dialog',
    templateUrl: './user-remove-dialog.component.html',
    styleUrls: ['./user-remove-dialog.component.css']
})
export class UserRemoveDialogComponent implements OnInit {

    @ViewChild(TableComponent) tbl: TableComponent;

    //  Form Settings
    addform: FormGroup;

    constructor(public dialogRef: MatDialogRef<UserRemoveDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private _userService: UserAccessService,
        private _appConfig: AppConfig,
        private _snackbar: MatSnackBar) { }

    ngOnInit() {
    }

    onClosedConfirm() {
        this.dialogRef.close('Closed');
    }

    removeUser() {
        this._userService.deleteUserToELK(this.data.userDelete).subscribe(data => {
            this.dialogRef.close('Closed');
            const message = 'Removing Record: ';
            this._snackbar.open(message, 'User successfully deleted', {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
        });
    }
}


