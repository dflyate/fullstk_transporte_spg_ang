import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { DriverService } from 'src/app/core/services/driver.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-driver-details',
  templateUrl: './driver-details.component.html',
  styleUrls: ['./driver-details.component.css']
})
export class DriverDetailsComponent implements OnInit{

  driverForm!: FormGroup;

  isEditMode = false;
  driverId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder, 
    private driverService: DriverService,
    private dialogService: DialogService,
  ) {
    this.driverForm = this.fb.group({
      documentType: [{ value: '', disabled: true }],
      documentNumber: [{ value: '', disabled: true }],
      fullName: [{ value: '', disabled: true }],
      address: [{ value: '', disabled: true }],
      city: [{ value: '', disabled: true }],
      department: [{ value: '', disabled: true }],
      country: [{ value: '', disabled: true }],
      phone: [{ value: '', disabled: true }],
    });
  }
  ngOnInit(): void {

    this.driverId = Number(this.route.snapshot.paramMap.get('id'));

    if(this.driverId != 0) {
      this.isEditMode = true;
      this.driverService.getDriverById(this.driverId).subscribe({
        next: (res) => {
          this.driverForm.patchValue(res);
        },
        error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
      });
    } 
  }


  getDriverFormControl(name: string): FormControl {
    return this.driverForm.get(name) as FormControl;
  }

}
