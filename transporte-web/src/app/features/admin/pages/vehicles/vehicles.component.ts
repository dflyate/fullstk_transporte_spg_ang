import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ConfirmationMessages, ErrorMessages } from 'src/app/core/constants/messages.constants';
import { ModalWindowsTypes } from 'src/app/core/constants/modal-windows-types';
import { Vehicle } from 'src/app/core/models/vehicle';
import { VehicleService } from 'src/app/core/services/vehicle.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {

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
    { icon: 'edit', tooltip: 'Editar', action: 'edit' },
    { icon: 'delete', tooltip: 'Eliminar', action: 'delete' },
  ];

  constructor(private vehicleService: VehicleService,
    private router: Router,
    private dialogService: DialogService) { }

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(pageIndex: number = 0, pageSize: number = 10) {
    this.isLoading = true;

    this.vehicleService.getPagedVehicles(pageIndex, pageSize).subscribe({

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

  goToNewVehicleForm(): void {
    this.router.navigate(['/admin/vehicles/new-vehicle']);
  }

  onTableAction(event: { action: string; row: any }) {
    const { action, row } = event;
    switch (action) {
      case 'edit':
        this.router.navigate(['/admin/vehicles/edit-vehicle', row.id]); 
        break;
      case 'delete':
        this.handleDeleteRow(row);
        break;
    }
  }

  handleDeleteRow(row: any) {
    this.dialogService.openDialog(ModalWindowsTypes.INFO,
      ConfirmationMessages.CONFIRMATION,
      ConfirmationMessages.DELETION_CONFIRMATION,
      {
        text: 'Eliminar vehiculo',
        onClick: () => this.deleteRow(row)
      });
  }

  deleteRow(row: any) {
    this.vehicleService.deleteVehicle(row.id).subscribe({
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
