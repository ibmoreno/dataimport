import { BaseEntity } from "@app/core/models/base-entity.model";

export class AccountingAccounts extends BaseEntity {

    static fromJson(jsonData: any): AccountingAccounts {
        return Object.assign(new AccountingAccounts(), jsonData);
    }

    static getInstace(): AccountingAccounts {
        return new AccountingAccounts();
    }

    constructor(
        public description?: string,
        public aggregateAccountId?: number,
        public active: boolean = true,
        public createdAt?: Date,
        public updatedAt?: Date,                    
    ) {
        super();
    }
}