import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { VehicleService } from 'src/app/core/services/vehicle.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {

  vehicleForm!: FormGroup;

  isEditMode = false;
  vehicleId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private dialogService: DialogService,
    private vehicleService: VehicleService
  ) {
    this.vehicleForm = this.fb.group({
      plate :[{ value: '', disabled: true }],
      engine :[{ value: '', disabled: true }],
      chassis :[{ value: '', disabled: true }],
      model :[{ value: '', disabled: true }],
      registrationDate :[{ value: '', disabled: true }],
      seatedPassengers :[{ value: '', disabled: true }],
      standingPassengers :[{ value: '', disabled: true }],
      dryWeight :[{ value: '', disabled: true }],
      grossWeight :[{ value: '', disabled: true }],
      doors :[{ value: '', disabled: true }],
      brand :[{ value: '', disabled: true }],
      line :[{ value: '', disabled: true }],
    });
  }
  ngOnInit(): void {

    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));

    if (this.vehicleId != 0) {
      this.isEditMode = true;
      this.vehicleService.getVehicleById(this.vehicleId).subscribe({
        next: (res) => {
          this.vehicleForm.patchValue(res);
        },
        error: (err) => {
          let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
          this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
        }
      });
    } 
  }

  getVehicleFormControl(name: string): FormControl {
    return this.vehicleForm.get(name) as FormControl;
  }
} 
