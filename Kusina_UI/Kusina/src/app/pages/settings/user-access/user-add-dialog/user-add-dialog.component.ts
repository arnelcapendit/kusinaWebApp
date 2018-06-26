import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { TableComponent } from '../../../../materials/table/table.component';
import { DataSourceService } from '../../../../services/datasource.service';
import { AppConfig } from '../../../../globals/app.config';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { Users } from '../../../../models/api.model';
import { UserAccessService } from '../../../../services/user-access.service';
import {MyErrorStateMatcher, errorMessages, ValidateUser} from '../../../../models/form-validation';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'app-user-add-dialog',
    templateUrl: './user-add-dialog.component.html',
    styleUrls: ['./user-add-dialog.component.css']
})


export class UserAddDialogComponent implements OnInit {

    toppings = new FormControl();

    toppingList = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];

    addForm: FormGroup;
    user: Users;
    newUser:any;
    
    matcher = new MyErrorStateMatcher();
    errors = errorMessages;

    constructor(
        public dialogRef: MatDialogRef<UserAddDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any,
        private fb: FormBuilder,
        private _userService: UserAccessService,
        private _snackbar: MatSnackBar,
        private _dataSourceService: DataSourceService,
        private _appConfig: AppConfig,
        private _http: HttpClient) {
            this.addForm =  fb.group({
                EID: ['', [Validators.compose([
                    Validators.required,
                    Validators.minLength(5),
                    Validators.maxLength(50),
                    // Validators.pattern('^[a-z]+\.+[a-z]$'),
                    // Validators.pattern('^[a-z?]+(\.[a-z]+)+(\.[a-z]+)+(\.[a-z]+)+(\.[a-z]+)?$'),
                    Validators.pattern('^[a-z.]+$'),
                    this.sampleBind(this._userService.dataUsers)
                    ])
                ]],
                ID: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    // Validators.maxLength(24),
                    Validators.pattern('^[0-9,]+$'),
                    // Validators.pattern('^[0-9][0-9][0-9][0-9]$')
                ]],
                AIRID: ['', [
                    Validators.required,
                    Validators.minLength(4),
                    // Validators.maxLength(24),
                    Validators.pattern('^[0-9,]+$'),
                    // Validators.pattern('^[0-9][0-9][0-9][0-9]$')
                ]],
                ACCESS: ['', Validators.required],
                EXPIRY_DATE: ['', [
                    Validators.required,
                    // Validators.minLength(8),
                    // Validators.maxLength(10),
                    // Validators.pattern('^[0-9]+/[0-9]+/+[0-9]$')
                ]],
                STATUS: ['', Validators.required]
                // CREATED_DATE: [''],
                // LAST_UPDATED_DATE: [''],
                // LAST_UPDATED_BY: [''],
            });
        }

    ngOnInit() {
    }   

    onClosedConfirm() {
        this.dialogRef.close('Closed');
        // console.log("This id data insert status: "+ this._userService.addResult.status);
    }

    addUser(any) {
        if (this.addForm.invalid) {
            const message = 'Invalid Action: ';
            this._snackbar.open(message, 'Please review all details before submitting', {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
            return;
        }

        this._userService.insertUserToELK(this.addForm.value).subscribe(data => {
                this.dialogRef.close('Closed');
                const message = 'Inserting New Record: ';
                this._snackbar.open(message, 'User successfully created', {
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
