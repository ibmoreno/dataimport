import { BaseEntity } from "@app/core/models/base-entity.model";

export class PageSort {

    constructor(
        public field?: string,
        public order?: string
    ) { }

}

export class PageableModel<T extends BaseEntity> {

    constructor(
        public content: T[] = [],
        public number: number = 0,
        public size: number = 10,
        public sort?: PageSort,
        public totalElements?: number,
        public totalPages?: number,
        public numberOfElements?: number
    ) { }

}