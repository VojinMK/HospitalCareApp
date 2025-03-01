import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Bolnica } from 'src/app/models/bolnica';
import { BolnicaService } from 'src/app/services/bolnica.service';
import { BolnicaDialogComponent } from '../../dialogs/bolnica-dialog/bolnica-dialog.component';

@Component({
  selector: 'app-bolnica',
  templateUrl: './bolnica.component.html',
  styleUrls: ['./bolnica.component.css']
})
export class BolnicaComponent implements OnInit, OnDestroy {

  displayedColumns = ['id','naziv','adresa','budzet','actions'];
  dataSource!: MatTableDataSource<Bolnica>;
  subscription!: Subscription;

  constructor(private service:BolnicaService, public dialog:MatDialog){}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit():void{
   this.loadData();
  }

  public loadData(){
   this.subscription= this.service.getAllBolnice().subscribe(
      (data)=> {
        //console.log(data);
        this.dataSource=new MatTableDataSource(data);
      }
    ),
    (error:Error)=>{
      console.log(error.name+ ' '+error.message);
    };
    
  }

  public openDialog(flag:number, id?:number, naziv?:string ,adresa?:string, budzet?:number){
     const dialogRef= this.dialog.open(BolnicaDialogComponent,{data:{id,naziv,adresa,budzet}});
     dialogRef.componentInstance.flag = flag; //u zavsinosti od flaga se prikazuje
     dialogRef.afterClosed().subscribe(
      (result)=>{
        if(result==1){
          this.loadData();
        }
      }
     )
    }
  }
