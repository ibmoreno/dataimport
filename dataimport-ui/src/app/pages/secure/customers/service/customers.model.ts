import { BaseEntity } from "@app/core/models/base-entity.model";

export class Customers extends BaseEntity {

    static fromJson(jsonData: any): Customers {
        return Object.assign(new Customers(), jsonData);
    }

    static getInstace(): Customers {
        return new Customers();
    }

    constructor(
        public name?: string,
        public cnpj?: string,
        public address?: string,
        public number?: string,
        public complement?: string,
        public city?: string,
        public state?: string,
        public zipCode?: string,
        public email?: string,
        public phone?: string,
        public active: boolean = true,
        public readModelVersion: string = "V01",   
        public createdAt?: Date,
        public updatedAt?: Date,                    
    ) {
        super();
    }
}
