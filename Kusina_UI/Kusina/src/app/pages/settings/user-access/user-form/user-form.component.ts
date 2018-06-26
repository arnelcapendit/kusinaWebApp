import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MyErrorStateMatcher, errorMessages } from '../../../../models/form-validation';
import { MatSnackBar, MatDialogRef } from '@angular/material';
import { AppConfig } from '../../../../globals/app.config';
import { AnnouncementsService } from '../../../../services/announcement.service';
import { ProfileService } from '../../../../services/profile.service';
import { UserAccessService } from '../../../../services/user-access.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

    data: any;
    cat: string;
    catName: string;
    isShow: boolean;
    showTeam: boolean;
    showAirID: boolean;
    userForm: FormGroup;
    eid = new FormControl();
    access = new FormControl();
    team = new FormControl();
    airid = new FormControl();
    site_id = new FormControl();
    status = new FormControl();
    expiredDate = new FormControl();



    matcher = new MyErrorStateMatcher();
    errors = errorMessages;

    @Input()
    set setData(val) {
        this.data = val;
    }

    @Input()
    set category(val) {
        this.cat = val;
    }

    // airid = new FormControl();
    // site_id = new FormControl();

    userAiridList = [];
    siteIdList = [];


    accesses = [
        {value: 'Super Administrator', viewValue: 'Super Administrator'},
        {value: 'Project Administrator', viewValue: 'Project Administrator'},
        {value: 'Operations Administrator', viewValue: 'Operations Administrator'},
    ];

    stat = [
        {value: 'Active', viewValue: 'Active'},
        {value: 'Inactive', viewValue: 'In Active'}
    ];

    constructor(
        private fb: FormBuilder,
        private _snackbar: MatSnackBar,
        private _appConfig: AppConfig,
        private _userService: UserAccessService,
        private _profileService: ProfileService,
        private _announcementsService: AnnouncementsService,
        public dialogRef: MatDialogRef<UserFormComponent>,
    ) {
            this.eid = new FormControl('', Validators.required);
            this.access = new FormControl('', Validators.required);
            // this.airid = new FormControl('', Validators.required);
            // this.site_id = new FormControl('', Validators.required);
            this.status = new FormControl('', Validators.required);
            this.expiredDate = new FormControl('', Validators.required);
            this.userForm = fb.group({
                'eid': this.eid,
                'access': this.access,
                'team': this.team,
                'airid': this.airid,
                'site_id': this.site_id,
                'status': this.status,
                'expiredDate': this.expiredDate
            });
    }

    ngOnInit() {
        this.getAllProfile();
        this.setForm();
        // console.log(this.data);

        if (this.cat === 'edit-user' ) {
            this.userForm.patchValue({
                eid: this.data.EID,
                access: this.data.ACCESS,
                team: this.data.TEAM,
                airid: this.data.AIRID.split(','),
                site_id: this.data.ID.split(','),
                status: this.data.STATUS,
                expiredDate: new Date(this.data.EXPIRY_DATE).toISOString()
            });
        }
    }

    setForm() {
        if ( this.cat === 'delete-user' ) {
            this.catName = 'Remove User';
            this.isShow = false;
        } else if ( this.cat === 'edit-user' ) {
            this.catName = 'Edit User';
            this.isShow = true;
            if ( this.data.ACCESS === 'Project Administrator' ) {
                // console.log('PA');
                this.showAirID = true;
                this.showTeam = false;
                this.team = new FormControl('');
                this.airid = new FormControl('',);
                this.site_id = new FormControl('',);
            } else if (this.data.ACCESS === 'Super Administrator') {
                // console.log('SA');
                this.showAirID = false;
                this.showTeam = true;
                this.team = new FormControl('',);
                this.airid = new FormControl('');
                this.site_id = new FormControl('');
            } else   if (this.data.ACCESS === 'Operations Administrator') {
                // console.log('OA');
                this.showAirID = true;
                this.showTeam = true;
                this.team = new FormControl('',);
                this.airid = new FormControl('',);
                this.site_id = new FormControl('',);
            }
            this.getSiteId(this.data.AIRID);
        } else {
            this.catName = 'Add User';
            this.isShow = true;
        }
    }

    getAllProfile() {
        this._profileService.getAllAirid().subscribe( data => {
            this.userAiridList = data;
            // console.log('AIRID: ', this.userAiridList);
        });
    }

    changeAccess(value) {
        console.log('changeAccess: ', value);
        if ( value === 'Project Administrator' ) {
            this.showAirID = true;
            this.showTeam = false;
            this.team = new FormControl('');
            this.airid = new FormControl('');
            this.site_id = new FormControl('');
            this.userForm.patchValue({
                team: '',
            });
        } else if (value === 'Super Administrator') {
            this.showAirID = false;
            this.showTeam = true;
            this.team = new FormControl('');
            this.airid = new FormControl('');
            this.site_id = new FormControl('');
            this.userForm.patchValue({
                airid: '',
                site_id: ''
            });
        } else {
            this.showAirID = true;
            this.showTeam = true;
            this.team = new FormControl('');
            this.airid = new FormControl('');
            this.site_id = new FormControl('');
        }
    }

    getSiteId(value) {
        const val = value.toString().replace(/,/g, '" OR "');
        const convertedSiteId = '"' + val + '"';

        this._profileService.getSiteId(convertedSiteId).subscribe( data => {
            // console.log(data);
            this.siteIdList = data;
        });
    }

    submitForm(value) {
        // console.log('Value: ', value);
        if (this.userForm.invalid) {
            const message = 'Invalid Action: ';
            this._snackbar.open(message, 'Please review all details before submitting', {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
            return;
        }

        if ( this.cat === 'add-user' ) {
            this._userService.insertUserToELK(value).subscribe(data => {
                this.dialogRef.close('Closed');
                const message = 'Inserting New Record: ';
                this._snackbar.open(message, 'User successfully created', {
                            duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                    });
            });
        } else if ( this.cat === 'edit-user' ) {
            this._userService.editUserToELK(value, this.data.CREATED_DATE).subscribe(data => {
                this.dialogRef.close('Closed');
                const message = 'Action: ';
                this._snackbar.open(message, data, {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
                this.userForm.reset();
            });
        }

    }

    deleteData() {
        this._userService.deleteUserToELK(this.data).subscribe(data => {
            this.dialogRef.close('Closed');
            const message = 'Action: ';
            this._snackbar.open(message, data, {
                    duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
            });
            this.userForm.reset();
        });
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }


}
