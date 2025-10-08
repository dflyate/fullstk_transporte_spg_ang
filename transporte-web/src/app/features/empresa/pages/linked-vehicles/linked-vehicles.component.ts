import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ConfirmationMessages, ErrorMessages } from 'src/app/core/constants/messages.constants';
import { ModalWindowsTypes } from 'src/app/core/constants/modal-windows-types';
import { Vehicle } from 'src/app/core/models/vehicle';
import { VehicleAffiliation } from 'src/app/core/models/vehicle-affiliation';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { VehicleService } from 'src/app/core/services/vehicle.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-linked-vehicles',
  templateUrl: './linked-vehicles.component.html',
  styleUrls: ['./linked-vehicles.component.css']
})
export class LinkedVehiclesComponent implements OnInit {

  displayedColumns = ['id', 'plate', 'engine', 'chassis', 'model', 'actions'];
  columnLabels = {
    id: 'ID',
    plate: 'Placa',
    engine: 'Motor',
    chassis: 'Chasis',
    model: 'Modelo',
    actions: 'Acciones'
  };

  data: Vehicle[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;
  isLoading = false;

  actionButtons = [
    { icon: 'visibility', tooltip: 'Ver', action: 'view' },
    { icon: 'link_off', tooltip: 'Desvincular', action: 'unlink' },
    { icon: 'person', tooltip: 'Vincular Conductores', action: 'linked_drivers' },
  ];

  constructor(private vehicleService: VehicleService,
    private router: Router,
    private contextService: UserContextService,
    private dialogService: DialogService) { }

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(pageIndex: number = 0, pageSize: number = 10) {
    this.isLoading = true;

    this.vehicleService.getAllByTransportCompany(pageIndex, pageSize, this.contextService.getTransportCompanyId()).subscribe({

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
    this.loadVehicles(event.pageIndex, event.pageSize);
  }

  goToUnlinkedVehicles(): void {
    this.router.navigate(['/company/vehicles/unlinked-vehicles']);
  }

  onTableAction(event: { action: string; row: any }) {
    const { action, row } = event;
    switch (action) {
      case 'view':
        this.router.navigate(['/company/vehicles/vehicle-details', row.id]);
        break;
      case 'unlink':
        this.handleLinkVehicleRow(row);
        break;
      case 'linked_drivers':
        this.router.navigate(['/company/vehicles/linked-drivers', row.id]);
        break;
    }
  }

  handleLinkVehicleRow(row: any) {
    this.dialogService.openDialog(ModalWindowsTypes.INFO,
      ConfirmationMessages.CONFIRMATION,
      ConfirmationMessages.UNLINK_VEHICLE_TO_COMPANY_CONFIRMATION,
      {
        text: 'Desvincular vehiculo',
        onClick: () => this.unlikeVehicle(row)
      });
  }

  unlikeVehicle(row: any) {
    const vehicleAffiliation: VehicleAffiliation = {
      vehicleId: row.id,
      transportCompanyId: this.contextService.getTransportCompanyId()
    };
    this.vehicleService.disaffiliate(vehicleAffiliation).subscribe({
      next: (res) => {
        this.dialogService.openDialog('error', ConfirmationMessages.CONFIRMATION, res.message);
        this.loadVehicles();
      },
      error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        this.dialogService.openDialog('error', ErrorMessages.ERROR, errorMessage);
      }
    })
  }

}
