import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationMessages, ErrorMessages } from 'src/app/core/constants/messages.constants';
import { ModalWindowsTypes } from 'src/app/core/constants/modal-windows-types';
import { Driver } from 'src/app/core/models/driver';
import { VehicleDriver } from 'src/app/core/models/vehicle-driver';
import { DriverService } from 'src/app/core/services/driver.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-linked-drivers',
  templateUrl: './linked-drivers.component.html',
  styleUrls: ['./linked-drivers.component.css']
})
export class LinkedDriversComponent implements OnInit {

  displayedColumns = ['id', 'documentType', 'documentNumber', 'fullName', 'address', 'actions'];
  columnLabels = {
    id: 'ID',
    documentType: 'Tipo de Documento',
    documentNumber: 'Documento',
    fullName: 'Nombre',
    address: 'Dirección',
    actions: 'Acciones'
  };

  data: Driver[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  isLoading = false;
  vehicleId: number = 0;

  actionButtons = [
    { icon: 'visibility', tooltip: 'Ver', action: 'view' },
    { icon: 'link_off', tooltip: 'Desvincular', action: 'unlink' },
  ];

  constructor(private driverService: DriverService,
    private route: ActivatedRoute,
    private contextService: UserContextService,
    private router: Router,
    private dialogService: DialogService) { }

  ngOnInit(): void {
    this.vehicleId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDrivers();
  }

  loadDrivers(pageIndex: number = 0, pageSize: number = 10) {
    this.isLoading = true;

    this.driverService.getAllByVehicle(pageIndex, pageSize, this.vehicleId).subscribe({

      next: (res) => {
        this.data = res.content;
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.pageIndex = res.number;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.loadDrivers(event.pageIndex, event.pageSize); 
  }

  goToUnlinkedVehicles(): void {
    this.router.navigate(['/company/vehicles/unlinked-drivers',this.vehicleId]);
  }

  onTableAction(event: { action: string; row: any }) {
    const { action, row } = event;
    switch (action) {
      case 'view':
        this.router.navigate(['/company/vehicles/driver-details', row.id]);
        break;
      case 'unlink':
        this.handleLinkDriverRow(row);
        break;
    }
  }

  handleLinkDriverRow(row: any) {
      this.dialogService.openDialog(ModalWindowsTypes.INFO,
        ConfirmationMessages.CONFIRMATION,
        ConfirmationMessages.UNLINK_DRIVER_TO_COMPANY_CONFIRMATION,
        {
          text: 'Desvincular conductor',
          onClick: () => this.unlinkeDriver(row)
        });
    }
  
    unlinkeDriver(row: any) {
      const vehicleDriver: VehicleDriver = {
        driverId: row.id,
        vehicleId: this.vehicleId
      };
      this.driverService.disaffiliate(vehicleDriver).subscribe({
        next: (res) => {
          this.dialogService.openDialog('error', ConfirmationMessages.CONFIRMATION, res.message);
          this.loadDrivers();
        },
        error: (err) => {
          let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
          this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
        }
      })
    }
} 
