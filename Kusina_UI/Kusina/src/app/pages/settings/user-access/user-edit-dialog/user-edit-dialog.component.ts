import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { TableComponent } from '../../../../materials/table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { UserAccessService } from '../../../../services/user-access.service';
import {MyErrorStateMatcher, errorMessages} from '../../../../models/form-validation';

@Component({
    selector: 'app-user-edit-dialog',
    templateUrl: './user-edit-dialog.component.html',
    styleUrls: ['./user-edit-dialog.component.css']
})
export class UserEditDialogComponent implements OnInit {

    //  Form Settings
    editForm: FormGroup;

    matcher = new MyErrorStateMatcher();
    errors = errorMessages;

    constructor(
        public dialogRef: MatDialogRef<UserEditDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder,
        private _userService: UserAccessService,
        private _appConfig: AppConfig,
        private _snackbar: MatSnackBar) {
            this.editForm = fb.group({
                EID: [this.data.userdata.EID, [Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(50),
                    // Validators.pattern('^[a-z]+\.+[a-z]$')
                    Validators.pattern('^[a-z.]+$'),
                    // this.sampleBind(this._userService.dataUsers)
                    ])
                ]],
                ID: [this.data.userdata.ID, [
                    Validators.required,
                    Validators.minLength(4),
                    // Validators.maxLength(24),
                    Validators.pattern('^[0-9,]+$'),
                    // Validators.pattern('^[0-9][0-9][0-9][0-9]$')
                ]],
                AIRID: [this.data.userdata.AIRID, [
                    Validators.required,
                    Validators.minLength(4),
                    // Validators.maxLength(24),
                    Validators.pattern('^[0-9,]+$'),
                    // Validators.pattern('^[0-9][0-9][0-9][0-9]$')
                ]],
                ACCESS: [this.data.userdata.ACCESS, Validators.required],
                EXPIRY_DATE: [this.data.userdata.EXPIRY_DATE, [
                    Validators.required
                ]],
                STATUS: [this.data.userdata.STATUS, Validators.required]
            // CREATED_DATE: [this.data.userdata.CREATED_DATE],
            // LAST_UPDATED_DATE: [this.data.userdata.LAST_UPDATED_DATE],
            // LAST_UPDATED_BY: [this.data.userdata.LAST_UPDATED_BY],
            });
        }

    ngOnInit() {
    }

    onClosedConfirm() {
        this.dialogRef.close('Closed');
    }

    editUser(any) {
        if (this.editForm.invalid) {
            const message = 'Invalid Action: ';
            this._snackbar.open(message, 'Please review all details before submitting', {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
            return;
        }
        this._userService.editUserToELK(
                // this.editForm.value.EID,
                // this.editForm.value.ID,
                // this.editForm.value.AIRID,
                // this.editForm.value.ACCESS,
                // this.editForm.value.EXPIRY_DATE,
                this.editForm.value.STATUS,
                this.data.userdata.CREATED_DATE
            ).subscribe(data => {
                this.dialogRef.close('Closed');
                const message = 'Inserting New Record: ';
                this._snackbar.open(message, 'User successfully updated', {
                            duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                    });
            });
    }

    sampleBind(data) {
        return function(control: FormControl) {
            const getPost =  data.find(x => x.EID === control.value);
            return getPost === undefined ? null : {
                sampleBind: true
            };
        };
    }

}
