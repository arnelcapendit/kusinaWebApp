import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MyErrorStateMatcher, errorMessages } from '../../../../models/form-validation';
import { MatSnackBar, MatDialogRef } from '@angular/material';
import { AppConfig } from '../../../../globals/app.config';
import { ProfileService } from '../../../../services/profile.service';

@Component({
  selector: 'app-profile-forms',
  templateUrl: './profile-forms.component.html',
  styleUrls: ['./profile-forms.component.css']
})
export class ProfileFormsComponent implements OnInit {


  data: any;
  cat: string;
  catName: string;
  isShow: boolean;
  profileForm: FormGroup;

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

  constructor(
    private fb: FormBuilder,
    private _snackbar: MatSnackBar,
    private _appConfig: AppConfig,
    private _profileService: ProfileService,
    public dialogRef: MatDialogRef<ProfileFormsComponent>,
  ) {
          this.profileForm = fb.group({
              app_name: ['', Validators.required ],
              airid: ['', Validators.required ],
              site_id: ['', Validators.required ],
          });
   }

  ngOnInit() {
    this.setForm();

    if (this.cat === 'edit-profile' ) {
      this.profileForm.patchValue({
        app_name: this.data.APP_NAME,
        airid: this.data.AIRID,
        site_id: this.data.SITE_ID,
      });
    }
  }

  setForm() {
      if ( this.cat === 'delete-profile' ) {
          this.catName = 'Remove App Profile';
          this.isShow = false;
      } else if ( this.cat === 'edit-profile' ) {
          this.catName = 'Edit App Profile';
          this.isShow = true;
      } else {
          this.catName = 'Add App Profile';
          this.isShow = true;
      }
  }

  submitForm(value) {

      if (this.profileForm.invalid) {
          const message = 'Invalid Action: ';
          this._snackbar.open(message, 'Please review all details before submitting', {
                      duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
              });
          return;
      }

      if ( this.cat === 'add-profile' ) {
          this._profileService.addProfile(value).subscribe(data => {
              const message = 'Action: ';
              this._snackbar.open(message, data, {
                      duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
              });
              this.profileForm.reset();
              this.dialogRef.close('Closed');
          });
      } else if ( this.cat === 'edit-profile' ) {
          this._profileService.editProfile(value, this.data.ID, this.data.CREATED_DATE).subscribe(data => {
              this.dialogRef.close('Closed');
              const message = 'Action: ';
              this._snackbar.open(message, data, {
                      duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
              });
              this.profileForm.reset();
          });
      }

  }

  deleteData() {
      this._profileService.deleteProfile(this.data).subscribe(data => {
          this.dialogRef.close('Closed');
          const message = 'Action: ';
          this._snackbar.open(message, data, {
                  duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
          });
          this.profileForm.reset();
      });
  }

  onClosed() {
      this.dialogRef.close('Closed');
  }

}
