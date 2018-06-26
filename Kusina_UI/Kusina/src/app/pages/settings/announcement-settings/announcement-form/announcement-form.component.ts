import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MyErrorStateMatcher, errorMessages } from '../../../../models/form-validation';
import { MatSnackBar, MatDialogRef } from '@angular/material';
import { AppConfig } from '../../../../globals/app.config';
import { AnnouncementsService } from '../../../../services/announcement.service';

@Component({
  selector: 'app-announcement-form',
  templateUrl: './announcement-form.component.html',
  styleUrls: ['./announcement-form.component.css']
})
export class AnnouncementFormComponent implements OnInit {

    data: any;
    cat: string;
    catName: string;
    isShow: boolean;
    sample = 'This is [google.com].';
    announcementForm: FormGroup;
    historyId: string;
    

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

    types = [
      {value: 'action', viewValue: 'Action'},
      {value: 'notification', viewValue: 'Notification'}
    ];

    status = [
      {value: 'draft', viewValue: 'Draft'},
      {value: 'live', viewValue: 'Live'}
    ];

    constructor(
      private fb: FormBuilder,
      private _snackbar: MatSnackBar,
      private _appConfig: AppConfig,
      private _announcementsService: AnnouncementsService,
      public dialogRef: MatDialogRef<AnnouncementFormComponent>,
    ) {
            this.announcementForm = fb.group({
                title: ['', [
                    Validators.required,
                    // Validators.pattern(/^[\w\-.,\s]+$/)
                ]],
                type: ['', Validators.required ],
                dueDate: ['', Validators.required ],
                status: ['', Validators.required ],
                content: ['', [
                    Validators.required,
                    // Validators.pattern(/^[\w\-.,:^#~*+-_%=;`"&()/\//\]\\[\?\s]+$/)
                ]]
            });
     }

    ngOnInit() {
      this.setForm();

      if (this.cat === 'edit-announcement' ) {
        this.announcementForm.patchValue({
          title: this.data.TITLE,
          type: this.data.TYPE,
          dueDate: new Date(this.data.DUE_DATE).toISOString(),
          status: this.data.STATUS,
          content: this.data.CONTENT
        });
      }
    }

    setForm() {
        if ( this.cat === 'delete-announcement' ) {
            this.catName = 'Remove Announcement';
            this.isShow = false;
        } else if ( this.cat === 'edit-announcement' ) {
            this.catName = 'Edit Announcement';
            this.isShow = true;
        } else {
            this.catName = 'Add Announcement';
            this.isShow = true;
        }
    }

    submitForm(value) {

        if (this.announcementForm.invalid) {
            const message = 'Invalid Action: ';
            this._snackbar.open(message, 'Please review all details before submitting', {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
            return;
        }

        if ( this.cat === 'add-announcement' ) {
            this._announcementsService.addAnnouncement(value).subscribe(data => {
                const message = 'Action: ';
                this._snackbar.open(message, data, {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
                this.announcementForm.reset();
                this.dialogRef.close('Closed');
            });
        } else if ( this.cat === 'edit-announcement' ) {
            this._announcementsService.editAnnouncement(value, this.data.ID, this.data.CREATED_DATE).subscribe(data => {
                this.dialogRef.close('Closed');
                const message = 'Action: ';
                this._snackbar.open(message, data, {
                        duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
                });
                this.announcementForm.reset();
            });
        }

    }

    deleteData() {
        this._announcementsService.deleteAnnouncement(this.data).subscribe(data => {
            this.dialogRef.close('Closed');
            const message = 'Action: ';
            this._snackbar.open(message, data, {
                    duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
            });
            this.announcementForm.reset();
        });
        this._announcementsService.getHistoryIdData(this.data).subscribe(data => {
            this.historyId = data;
            console.log("This is history id: " + data);
            this.deleteHistoryData(this.historyId);
        });
    }

    onClosed() {
        this.dialogRef.close('Closed');
    }

    deleteHistoryData(id){
        this._announcementsService.deleteHistoryId(id).subscribe(data => {
            console.log("Deleted");
            this.dialogRef.close('Closed');
        });
    }

}
