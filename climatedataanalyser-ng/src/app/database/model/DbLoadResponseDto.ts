export class DbLoadResponseDto {

  isDbLoaded: string;
  lastLoad: string;
  status: string;
  dbLoadSteps: Array<DbLoadSteps>;

}

export class DbLoadSteps {
  stepName: string;
  startTime: string;
  stepEndTime: string;
  readCount: string;
  writeCount: string;
  stepStatus: string;
}
