import { BaseEntity } from "@app/core/models/base-entity.model";

export class AggregatorAccounts extends BaseEntity {

    static fromJson(jsonData: any): AggregatorAccounts {
        return Object.assign(new AggregatorAccounts(), jsonData);
    }

    static getInstace(): AggregatorAccounts {
        return new AggregatorAccounts();
    }

    constructor(
        public description?: string,
    ) {
        super();
    }
}