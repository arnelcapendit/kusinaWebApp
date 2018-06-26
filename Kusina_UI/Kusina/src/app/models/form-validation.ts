import { FormGroup, FormControl, FormGroupDirective, NgForm, ValidatorFn } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
      const isSubmitted = form && form.submitted;
      return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
  }

// export class MyErrorStateMatcher implements ErrorStateMatcher {
// 	isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
// 		return control.parent.invalid && control.touched;
// 	}
// }


/**
 * Collection of reusable error messages
 */
export function ValidateUser(control: FormControl) {
  if (!control.value.startsWith('https') || !control.value.includes('.io')) {
    return { validUrl: true };
  }
  return null;
}

export const errorMessages = {
    eid: {
      REQUIRED: 'EID is required',
      MIN: 'EID minimum is 5 characters',
      MAX: 'EID maximum is 25 characters',
      PATTERN: 'Invalid EID format',
      EXIST: 'EID already exist'
    },
    access: {
      REQUIRED: 'Access is required'
    },
    ID: {
      REQUIRED: 'ID is required',
      MIN: 'ID minimum is 4 characters',
      MAX: 'ID maximum is 5',
      PATTERN: 'Invalid ID format'
    },
    AIRID: {
      REQUIRED: 'AIRID is required',
      MIN: 'AIRID minimum is 4 characters',
      MAX: 'AIRID maximum is 5',
      PATTERN: 'Invalid AIRID format'
    },
    expiredDate: {
      REQUIRED: 'DATE is required',
      MIN: 'DATE minimum is 8 characters',
      MAX: 'DATE maximum is 10 characters',
      PATTERN: 'Invalid EXPIRY_DATE format'
    },
    STATUS: {
      REQUIRED: 'STATUS is required'
    },
    comment: {
      REQUIRED: 'Please add comments',
      PATTERN: 'Invalid special character'
    },
    module: {
      REQUIRED: 'Please choose module, this is required'
    },

    title: {
      REQUIRED: 'Title is required',
      PATTERN: 'Invalid special character'
    },

    dueDate: {
      REQUIRED: 'Due Date is required'
    },

    type: {
      REQUIRED: 'Type is required'
    },

    status: {
      REQUIRED: 'Status is required'
    },

    content: {
      REQUIRED: 'Content is required',
      PATTERN: 'Invalid special character'
    },

    app_name: {
      REQUIRED: 'App Name is required'
    },

    airid: {
      REQUIRED: 'AirID is required'
    },

    site_id: {
      REQUIRED: 'Site Id is required'
    },

    team: {
      REQUIRED: 'Team is required'
    },

};


