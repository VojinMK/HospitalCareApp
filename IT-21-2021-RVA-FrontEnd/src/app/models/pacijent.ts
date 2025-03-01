import { Dijagnoza } from "./dijagnoza";
import { Odeljenje } from "./odeljenje";

export class Pacijent{
    id!:number;
    ime!:string;
    prezime!:string;
    zdrOsiguranje!:boolean;
    datumRodjenja!:Date;
    odeljenje!:Odeljenje;
    dijagnoza!:Dijagnoza;
}