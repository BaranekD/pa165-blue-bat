import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function MinDateValidator(minDate: Date): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control && control.value && new Date(control.value) < minDate) {
      return { 'minDateValidator': true };
    }
    return null;
  };
}
