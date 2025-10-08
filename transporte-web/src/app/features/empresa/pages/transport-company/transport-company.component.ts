import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { TransportCompanyService } from 'src/app/core/services/transport-company.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-transport-company',
  templateUrl: './transport-company.component.html',
  styleUrls: ['./transport-company.component.css']
})
export class TransportCompanyComponent implements OnInit{

  transportForm!: FormGroup;
  legalRepresentativeForm!: FormGroup;
  mainForm!: FormGroup;

  isEditMode = false;
  transportCompanyId: number = 0; 

  constructor(
    private dialogService: DialogService,
    private userContext: UserContextService,
    private fb: FormBuilder, 
    private transportCompanyService: TransportCompanyService
  ) {
    this.transportForm = this.fb.group({
      documentType: [{ value: '', disabled: true }],
      documentNumber: [{ value: '', disabled: true }],
      fullName: [{ value: '', disabled: true }],
      address: [{ value: '', disabled: true }],
      city: [{ value: '', disabled: true }],
      department: [{ value: '', disabled: true }],
      country: [{ value: '', disabled: true }],
      phone: [{ value: '', disabled: true }],
    });

    this.legalRepresentativeForm = this.fb.group({
      documentType: [{ value: '', disabled: true }],
      documentNumber: [{ value: '', disabled: true }],
      fullName: [{ value: '', disabled: true }],
      address: [{ value: '', disabled: true }],
      city: [{ value: '', disabled: true }],
      department: [{ value: '', disabled: true }],
      country: [{ value: '', disabled: true }],
      phone: [{ value: '', disabled: true }],
    });

    this.mainForm = this.fb.group({
      transportForm: this.transportForm,
      legalRepresentativeForm: this.legalRepresentativeForm,
    });
  }
  ngOnInit(): void {

    this.transportCompanyId = this.userContext.getTransportCompanyId();

    if(this.transportCompanyId != 0) {
      this.isEditMode = true;
      this.transportCompanyService.getTransportCompanyById(this.transportCompanyId).subscribe({
        next: (res) => {
          this.transportForm.patchValue(res.transportCompany);
          this.legalRepresentativeForm.patchValue(res.legalRepresentative);
        },
        error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
      });
    } 
  }

  getTransportFormControl(name: string): FormControl {
    return this.transportForm.get(name) as FormControl;
  }

  getLegalRepControl(name: string): FormControl {
    return this.legalRepresentativeForm.get(name) as FormControl;
  }

}
