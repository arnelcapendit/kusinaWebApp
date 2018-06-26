
export class UserModel{

    private eid:string;
    private auth_time:number;
    private exp_time:number;
    private nonce_id:string;
    private display_name:string;

    constructor(eid:string, auth_time:number, exp_time:number, nonce_id:string , display_name:string) {
        this.eid=eid;
        this.auth_time=auth_time;
        this.exp_time=exp_time;
        this.nonce_id=nonce_id;
        this.display_name=display_name;
    }

    public getEID():string{
        return this.eid;
    }
    public getDisplayName():string{
        return this.display_name;
    }
    public getAuthTime():number{
        return this.auth_time;
    }
    public getExpTime():number{
        return this.exp_time;
    }
    public getNonceID():string{
        return this.nonce_id;
    }

}
