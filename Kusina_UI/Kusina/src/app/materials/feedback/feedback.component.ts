import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CustomReportService } from '../../services/custom-report.service';
import { AppConfig } from '../../globals/app.config';
import { MatSnackBar } from '@angular/material';
import { FeedbackService } from '../../services/feedback.service';
import { errorMessages, MyErrorStateMatcher } from '../../models/form-validation';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit, AfterViewInit {

  giveFeedbackForm: FormGroup;
  rateValue: 0;
  showFeed: boolean;

  matcher = new MyErrorStateMatcher();
  errors = errorMessages;

  constructor(
    private fb: FormBuilder,
    private _snackbar: MatSnackBar,
    private _appConfig: AppConfig,
    private feedbackService: FeedbackService) {
    this.giveFeedbackForm = fb.group({
        module: ['', Validators.required ],
        comment: ['', [
            Validators.required,
            Validators.pattern(/^[\w\-.,?\s]+$/)
        ]]
    });
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
  }

  toggleClick() {

    this.showFeed = !this.showFeed;
  }

  getRateValue($event) {
    this.rateValue = $event;
  }

  sendFeedback(value) {
    if (this.giveFeedbackForm.invalid) {
        const message = 'Invalid Action: ';
        this._snackbar.open(message, 'Please review all details before submitting', {
                    duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
            });
        return;
    }

    this.feedbackService.addFeedback(value, this.rateValue).subscribe(data => {
        const message = 'Success: ';
        this._snackbar.open(message, data, {
                duration: this._appConfig.SNACKBAR.SNACKBAR_DURATION,
        });
        this.giveFeedbackForm.reset();
        this.showFeed = false;
    });
  }

}
