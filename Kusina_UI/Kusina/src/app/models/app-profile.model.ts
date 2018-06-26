export class AppProfileList {

    AppNameList: AppProfileModel[];
    UserDetails: any;
   
    constructor() {

    }

   
}

export class AppProfileModel {

    private appName: string;
     airId: string;
    private id: string;
    private formattedValue: string;

    constructor(appName: string, airId: string, id: string) {
        this.appName = appName;
        this.airId = airId;
        this.id = id;
        this.setFormattedValue();
    }

    public getAppName(): string {
        return this.appName;
    }
    public getAirId(): string {
        return this.airId;
    }
    public getId(): string {
        return this.id;
    }
    public getFormattedValue() {
        this.setFormattedValue();
        return this.formattedValue;
    }
    private setFormattedValue() {
        this.formattedValue = '[' + this.airId + '] ' + this.appName;
    }
    public deserialize(input) {
        this.appName = 'appName';
        this.airId = 'airId';
        this.id = 'id';
        return this;
    }
}
