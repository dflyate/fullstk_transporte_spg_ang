import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { Vehicle } from 'src/app/core/models/vehicle';
import { CatalogService } from 'src/app/core/services/catalog.service';
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
    private router: Router,
    private vehicleService: VehicleService
  ) {
    this.vehicleForm = this.fb.group({
      plate : ['', [Validators.required, Validators.maxLength(10)]],
      engine : ['', [Validators.required, Validators.maxLength(50)]],
      chassis : ['', [Validators.required, Validators.maxLength(50)]],
      model : ['', [Validators.required, Validators.maxLength(20)]],
      registrationDate : [''],
      seatedPassengers : [''],
      standingPassengers : ['', [Validators.required, Validators.maxLength(5)]],
      dryWeight : ['', [Validators.required, Validators.maxLength(5)]],
      grossWeight : ['', [Validators.required, Validators.maxLength(5)]],
      doors : ['', [Validators.required, Validators.maxLength(2)]],
      brand : ['', [Validators.required, Validators.maxLength(50)]],
      line : ['', [Validators.required, Validators.maxLength(50)]],
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

  onSubmit(): void {
    if (this.vehicleForm.invalid) return;

    const vehicle: Vehicle = this.vehicleForm.value;
    let save$;
    if (this.isEditMode) {
      vehicle.id = this.vehicleId;
      save$ = this.vehicleService.updateVehicle(vehicle)
    } else {
      save$ = this.vehicleService.createVehicle(vehicle);
    }

    save$.subscribe({
      next: (response: any) => {
        try {
          this.router.navigate(['/admin/vehicles']);
          this.dialogService.openDialog('success', 'Éxito', response?.message);
        } catch (e) {
          this.dialogService.openDialog('error', 'Error', "Error al guardar un registro");
        }
      },
      error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', 'Error', errorMessage);

      }
    });
  }


  getVehicleFormControl(name: string): FormControl {
    return this.vehicleForm.get(name) as FormControl;
  }
} 
