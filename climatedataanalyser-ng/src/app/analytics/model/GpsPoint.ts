export class  GpsPoint {

  // längengrad  -180 , 0 , 180
  longitude: number ;

  // breitengrad -90, 0 , 90
  latitude: number ;

  constructor( long: number ,lat:number) {
  this.longitude = long;
  this.latitude = lat
  }

}
