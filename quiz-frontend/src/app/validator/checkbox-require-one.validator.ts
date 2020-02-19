import { FormArray } from '@angular/forms';

export class CheckboxRequireOneValidator {
  static multipleCheckboxRequireOne(fa: FormArray) {
    let checked = 0;

    Object.keys(fa.controls).forEach(key => {
      const control = fa.controls[key];

      if (control.value === true) {
        checked ++;
      }
    });

    if (checked < 1) {
      return {
        requireOneCheckboxToBeChecked: true,
      };
    }

    return null;
  }
}
