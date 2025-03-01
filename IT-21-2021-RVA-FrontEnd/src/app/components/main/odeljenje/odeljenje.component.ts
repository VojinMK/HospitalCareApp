import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { Bolnica } from 'src/app/models/bolnica';
import { Odeljenje } from 'src/app/models/odeljenje';
import { OdeljenjeService } from 'src/app/services/odeljenje.service';
import { OdeljenjeDialogComponent } from '../../dialogs/odeljenje-dialog/odeljenje-dialog.component';

@Component({
  selector: 'app-odeljenje',
  templateUrl: './odeljenje.component.html',
  styleUrls: ['./odeljenje.component.css']
})
export class OdeljenjeComponent implements OnInit, OnDestroy{
  
  displayedColumns = ['id','naziv','lokacija','bolnica','actions'];
  dataSource!: MatTableDataSource<Odeljenje>;
  subscription!: Subscription;

  constructor(private service:OdeljenjeService, public dialog:MatDialog){}

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit():void{
   this.loadData();
  }

  public loadData(){
   this.subscription= this.service.getAllOdeljenje().subscribe(
      (data)=> {
        //console.log(data);
        this.dataSource=new MatTableDataSource(data);
      }
    ),
    (error:Error)=>{
      console.log(error.name+ ' '+error.message);
    };
    
  }

  public openDialog(flag:number, id?:number, naziv?:string ,lokacija?:string, bolnica?:Bolnica){
     const dialogRef= this.dialog.open(OdeljenjeDialogComponent,{data:{id,naziv,lokacija,bolnica}});
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
